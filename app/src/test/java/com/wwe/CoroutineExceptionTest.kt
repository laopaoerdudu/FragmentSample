package com.wwe

import kotlinx.coroutines.*
import org.junit.Test

class CoroutineExceptionTest {

    @Test
    fun test() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            try {
                throw RuntimeException("RuntimeException in coroutine")
            } catch (exception: Exception) {
                println("Handle $exception")
            }
        }
        Thread.sleep(100)
        println("Done")
    }

    @Test
    fun test2() {
        val topLevelScope = CoroutineScope(Job())
        try {
            topLevelScope.launch {
                throw RuntimeException("RuntimeException in coroutine")
            }
        } catch (exception: Exception) {
            println("Handle $exception")
        }
        Thread.sleep(100)
    }

    @Test
    fun test3() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            try {
                /**
                 * 捕获失效了，并且app crash了。我们发现try catch无法catch住子协程的异常。
                 * 传播的异常可以通过 CoroutineExceptionHandler 来捕获，如果没有设置，则将调用线程的未捕获异常处理程序，可能会导致退出应用。
                 */
                launch {
                    throw RuntimeException("RuntimeException in nested coroutine")
                }
            } catch (exception: Exception) {
                println("Handle $exception")
            }
        }
        Thread.sleep(100)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Handle $exception in CoroutineExceptionHandler")
    }

    @Test
    fun test4() {
        val topLevelScope = CoroutineScope(Job() + coroutineExceptionHandler)

        topLevelScope.launch {
            /** 给子协程设置 CoroutineExceptionHandler 是没有效果的，我们必须给顶级协程设置，或者初始化 Scope 时设置才有效。*/
            launch {
                throw RuntimeException("RuntimeException in nested coroutine")
            }
        }

        Thread.sleep(100)
    }

    @Test
    fun test5() {
        val topLevelScope = CoroutineScope(SupervisorJob())

        val deferredResult = topLevelScope.async {
            throw RuntimeException("RuntimeException in async coroutine")
        }

        topLevelScope.launch {
            try {
                /** 如果顶级协程以 async 方式启动，则异常封装在Deferred返回类型中，并在调用.await()时重新抛出。 */
                deferredResult.await()
            } catch (exception: Exception) {
                println("Handle $exception in try/catch")
            }
        }

        Thread.sleep(100)
    }

    @Test
    fun test6() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Handle $exception in CoroutineExceptionHandler")
        }

        val topLevelScope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)
        topLevelScope.launch {
            async {
                throw RuntimeException("RuntimeException in async coroutine")
            }
        }
        Thread.sleep(100)
    }

    @Test
    fun test7() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Handle $exception in CoroutineExceptionHandler")
        }

        val topLevelScope = CoroutineScope(Job())

        topLevelScope.launch {
            val job1 = launch {
                println("starting Coroutine 1")
            }

            supervisorScope {
                val job2 = launch(coroutineExceptionHandler) {
                    println("starting Coroutine 2")
                    throw RuntimeException("Exception in Coroutine 2")
                }

                val job3 = launch {
                    println("starting Coroutine 3")
                }
            }
        }

        Thread.sleep(100)
    }

    @Test
    fun test8() {

    }
}