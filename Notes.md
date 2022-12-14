## Fragment 生命周期与宿主同步的吗，如果不是，是独立的吗？

Fragment 的生命周期主要受「宿主」、「事务」、「setRetainInstance() API」三个因素影响：
当宿主生命周期发生变化时，会触发 Fragment 状态转移到 宿主的最新状态。
不过，使用事务和 `setRetainInstance()` API 也可以使 Fragment 在一定程度上与宿主状态不同步（需要注意：宿主依然在一定程度上形成约束）。

当宿主生命周期发生变化时，Fragment 的状态会同步到宿主的状态。从源码看，体现在宿主生命周期回调中会调用 FragmentManager 中一系列 `dispatchXXX()` 
方法来触发 Fragment 状态转移。

```
# FragmentActivity.java

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    mFragments.attachHost(null /*parent*/);
    ...
    mFragments.dispatchCreate(); // 最终调用 FragmentManager #dispatchCreate()
}
```

FragmentManager 中一系列 `dispatchXXX()` 方法会触发 Fragment 状态转移。

**基本规律：Activity 状态转移触发 Fragment 状态转移。**

```
首次启动：
Activity - onCreate
Fragment - onAttach
Fragment - onCreate
Fragment - onCreateView
Fragment - onViewCreated
Activity - onStart
Fragment - onActivityCreated
Fragment - onStart
Activity - onResume
Fragment - onResume
-------------------------------------------------
退出：
Activity - onPause
Fragment - onPause
Activity - onStop
Fragment - onStop
Activity - onDestroy
Fragment - onDestroyView
Fragment - onDestroy
Fragment - onDetach
-------------------------------------------------
退到后台：
Activity - onPause
Fragment - onPause
Activity - onStop
Fragment - onStop
-------------------------------------------------
回到前台：
Activity - onStart
Fragment - onStart
Activity - onResume
Fragment - onResume
-------------------------------------------------
屏幕旋转：
Fragment onPause
Activity onPause
Fragment onStop
Activity onStop
Fragment onSaveInstanceState
Activity onSaveInstanceState
Fragment onDestroyView
Fragment onDestroy
Activity onDestroy
Fragment onCreate
Activity onRestoreInstanceState
Fragment onResume
-------------------------------------------------
detach Fragment：
Fragment - onPause
Fragment - onStop
Fragment - onDestroyView
```

## 事务的特性是什么？

1、原子性：事务不可分割，要么全部完成，要么全部失败回滚；

2、一致性：事务执行前后数据都具有一致性；

3、隔离性：事务执行过程中，不受其他事务干扰；

4、持久性：事务一旦完成，对数据的改变就是永久的。
>在 Android 中体现为 Fragment 状态保存后，commit() 提交事务会抛异常，因为这部分新提交的事务影响的状态无法保存。

show & hide： 只控制 Fragment 隐藏或显示，不会触发状态转移，也不会销毁 Fragment 视图或实例。

replace： 先移除所有 containerId 中的实例，再 add 一个 Fragment。

## 事务提交你需要注意的地方：

1, 使用 `commitNow()` 或 `commitNowAllowingStateLoss()` 提交的事务不允许加入回退栈。

>为什么有这个设计呢？可能是 Google 考虑到同时存在同步提交和异步提交的事务，并且两个事务都要加入回退栈时，
无法确定哪个在上哪个在下是符合预期的，所以干脆禁止 `commitNow()` 加入回退栈。
如果确实有需要同步执行+回退栈的应用场景，可以采用 `commit() + executePendingTransactions()` 的取巧方法。

```
# BackStackRecord.java

@Override
public void commitNow() {
    disallowAddToBackStack();
    mManager.execSingleAction(this, false);
}

@Override
public void commitNowAllowingStateLoss() {
    disallowAddToBackStack();
    mManager.execSingleAction(this, true);
}
@NonNull
public FragmentTransaction disallowAddToBackStack() {
    if (mAddToBackStack) {
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }
    mAllowAddToBackStack = false;
    return this;
} 
```

2, `commitNow()` 和 `executePendingTransactions()` 都是同步执行，有区别吗？

>`commitNow()` 是同步执行当前事务，而 `executePendingTransactions()` 是同步执行事务队列中的全部事务。

## `setRetainInstance()` 到底做了什么？

>事实上，从 androidx.fragment 1.3.0 开始，setRetainInstance() 这个 API 已经废弃了。
不过，考虑到这个 API 的重要性，我们还是花费一点时间来回顾一下。

1，什么时候应该使用 `setRetainInstance(true)`？

>在配置变更时（例如屏幕旋转），整个 Activity 销毁重建，顺带着 Activity 中的 Fragment 也销毁重建。
而设置 `setRetainInstance(true)` 的 Fragment 对象在 Activity 销毁重建的过程中不会被销毁。

2，`setRetainInstance(true)` 对 Fragment 生命周期的影响？

>在 Activity 销毁时，Fragment 不会回调 `onDestroy()`，而是直接回调 `onDestroy() + onDetach()`；
在 Activity 重建时，Fragment 不会回调 onCreate()，而是直接调用 onResume()。

3，为什么废弃 `setRetainInstance()`？

>引入 ViewModel 后，setRetainInstance() API 开始变得鸡肋。
ViewModel 已经提供了在 Activity 重建等场景下保留数据的能力，
虽然 setRetainInstance() 也具备相同功能，但需要利用 Fragment 来间接存储数据，使用起来不方便，存储粒度也过大。

## ViewModel 为什么可以恢复数据？

>简单来说，在 Activity 销毁时，最终会调用 Activity #retainNonConfigurationInstances() 保存 `ActivityClientRecord`，
并托管给 ActivityManagerService。这个过程就相当于把 Fragment 保存到更长的生命周期了。











































