协程的取消是通过抛出 `CancellationException` 来进行的，神奇的是抛出 Exception 并没有导致应用程序 Crash。

>如果协程的 Job 被取消，则由可取消的挂起函数抛出 `CancellationException`。它表示协程的正常取消。
在默认的 CoroutineExceptionHandler 下，它不会打印到控制台/日志。

**通常， 所有子协程都将异常的处理委托给他们的父协程，父协程也委托给它自身的父协程，直到委托给根协程处理。
所以在子协程中的 `CoroutineExceptionHandler` 永远不会被使用。**

默认情况下，如果协程没有配置用于处理异常的 Handler ，未捕获的异常将按以下方式处理:

如果 exception 是 CancellationException ，那么它将被忽略(因为这是取消正在运行的协程的假定机制)。

使用 try-catch 来捕获 CancellationException 时需要注意，在挂起函数前的代码逻辑仍会多次执行，从而导致这部分代码仿佛没有被取消一样。

kotlinx.coroutines 中所有挂起函数（带有 suspend 关键字函数）都是可以被取消的。
suspend 函数会检查协程是否需要取消并在取消时抛出 CancellationException 。

## 如何写出可以取消的代码？

- 显式检查取消状态

>`isActive` 是通过 `CoroutineScope` 对象在协程内部可用的扩展属性。

```
while (isActive) { ... }
```

- 使用 `try{…} finally{…}`

>使用 `try-catch` 捕获 `CancellationException` 发现会产生父协程等待所有子协程完成后才能完成，
所以建议不用 try-catch 而是 try{…} finally{…} ，让父协程在被取消时正常执行终结操作:

## 协程的原理

1，协程的取消机制是通过挂起函数的挂起点检查来进行取消检查的。证实了为什么如果没有 suspend 函数（本质是挂起点），协程的取消就不会生效。

2，协程的取消机制是需要函数合作的，就是通过 suspend 函数来增加取消检查的时机。

3，父协程会执行完所有的子协程（挂起函数），因为代码的本质是一个循环执行 switch 语句， 当一个子协程（或挂起函数）执行结束，会继续执行到下一个分支。
但是最后一个挂起点后续的代码并不会被执行，因为最后一个挂起点检查到失败，不会继续跳到最后的 label 分支。

4，`invokeSuspend` 是一个抽象方法，当协程从挂起状态想要恢复时，就得调用这个 `invokeSuspend`，这里是恢复的入口就行。
`invokeSuspend` 内部会把结果（这个结果可能是正常的结果，也可能是 Exception）取出来，开启协程状态机。

5，返回值变成了 Object。为啥是 Object？它有什么用？这个返回值其实是用来标识该函数是否挂起的标志，
如果返回值是 `Intrinsics.COROUTINE_SUSPENDED`，那么说明该函数被挂起了
（挂起函数的结果不是通过函数返回值来获取的，而是通过 `Continuation`，也就是 `Callback` 回调得到的结果）。
如果该函数是伪挂起函数（里面没有其他挂起函数，但还是会进行CPS转换），则是直接返回结果。

6，当挂起函数经过编译之后，会变成 switch 和 label 组成的状态机结构。
label 代表了当前状态机的具体状态，每改变一次，就代表挂起函数被调用一次。
在里面会创建一个 Callback 接口，当挂起之后，挂起函数的结果返回是通过 Callback 回调回来的，回调回来之后，
因为之前修改过 label，根据该 label 来判断该继续往下走了，执行后面的逻辑。
上面的 Callback 就是 Continuation，我觉得它在这里的意思可以翻译成继续执行剩余的代码。

## Try Catch 与 CoroutineExceptionHandler 对比

- 如果需要在代码的特定部分处理异常，建议在协程内部的相应代码周围使用 try / catch。
这样， 您可以防止协程异常完成（现在已捕获异常），重试该操作和/或采取其他任意操作：”

- `CoroutineExceptionHandler` 是用于全局“全部捕获”行为的最后手段。
您无法从 CoroutineExceptionHandler 中的异常中恢复。
当调用处理程序时，协程已经完成，并带有相应的异常。 
通常，处理程序用于记录异常，显示某种错误消息，终止和/或重新启动应用程序。

我们绝大多数时候应该使用 CoroutineExceptionHandler。CoroutineExceptionHandler 无法在代码的特定部分处理异常，
例如针对某一个失败接口，无法在异常后进行重试或者其他特定操作。 如果你想在特定部分做异常处理的话，try-catch更适合。

```
interface ProjectApi {
    @GET("project/tree/json")
    suspend fun loadProjectTree(): BaseResp<List<ProjectTree>>

    @GET("project/tree/jsonError")
    suspend fun loadProjectTreeError(): BaseResp<List<ProjectTree>>
}

 suspend fun loadProjectTree() {
        try {
            val errorResult = service.loadProjectTreeError()
            Log.d(TAG, "loadProjectTree errorResult: $errorResult")
        } catch (e: Exception) {
            Log.d(TAG, "loadProjectTree: error Exception  " + e.message)
            e.printStackTrace()
        }

        try {
            val result = service.loadProjectTree()
            Log.d(TAG, "loadProjectTree: $result")
        } catch (e: Exception) {
            Log.d(TAG, "loadProjectTree: Exception  " + e.message)
            e.printStackTrace()
        }
    }

打印结果如下：
loadProjectTree: error Exception HTTP 404 Not Found
loadProjectTree: com.fuusy.common.network.BaseResp@57e153d
```

协程最大的优点是可以使用同步的方法写异步代码，CoroutineExceptionHandler 有以下缺点：

1，将异常处理代码与协程代码分隔开了，看上去不是同步代码。

2，每次使用都要新建局部变量，不够优雅。

## 什么是协程的结构化并发？

在 Kotlin 的协程中，全局的 GlobalScope 是一个作用域，每个协程自身也是一个作用域，新建的协程与它的父作用域存在一个级联的关系，也就是一个父子关系层次结构。
而这级联关系主要在于：

1、父作用域的生命周期持续到所有子作用域执行完；

2、当结束父作用域结束时，同时结束它的各个子作用域；

3、子作用域未捕获到的异常将不会被重新抛出，而是一级一级向父作用域传递，这种异常传播将导致父作用域失败，进而导致其子作用域的所有请求被取消。

上面的三点也就是协程结构化并发的特性。

了解了什么是协程的结构化并发，那我们就又回到 try-catch 为什么在协程中开启一个失败的子协程的情况下会失败的问题上。
很显然，上面第3点就是这个问题的答案，子协程中未捕获的异常不会被重新抛出，而是在父子层次结构中向上传播，这种异常传播将导致父 Job 失败。

## 协程异常处理的结论：

1，在代码的特定部分处理异常，可使用try-catch。

2，全局捕获异常，并且其中一个任务异常，其他任务不执行，可使用 CoroutineExceptionHandler，节省资源消耗。

3，并行任务间互不干扰，任何一个任务失败，其他任务照常运行，可使用 SupervisorScope+async 模式。

```
viewModelScope.launch() {
            supervisorScope {
                try {
                    //除数为0，抛异常
                    val deferredFail = async { 2 / 0 }
                    
                    val deferred = async {
                        2 / 1
                        Log.d(TAG, "loadProjectTree:  2/1 ")
                    }
                    deferredFail.await()
                    deferred.await()

                } catch (e: Exception) {
                    Log.d(TAG, "loadProjectTree:Exception ${e.message} ")
                }
            }
        }        
```





