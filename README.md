**`setReorderingAllowed(true)`**

>This method is actually optional, however, the documentation strongly recommends that we add it. 
This method enables some optimizations when multiple transactions execute together. 
Also, if any fragments are added and then immediately replaced, 
this method makes sure that they do not go through any lifecycle methods.

## `popBackStack(String name, int flag)`

通过 name 能找到回退栈的特定元素，flag可以为 0 或者 `FragmentManager.POP_BACK_STACK_INCLUSIVE`，
0 表示只弹出该元素以上的所有元素，而 `POP_BACK_STACK_INCLUSIVE` 表示弹出包含该元素及以上的所有元素。
这里说的弹出所有元素包含回退这些事务。

fragment 拦截 activity 返回栈是通过 `OnBackPressedDispatcher` 实现的，如果开启事务调用了 `addToBackStack` 方法，
则 `mOnBackPressedCallback` 的 isEnabled 属性会赋值为 true，进而起到拦截 activity 返回逻辑的作用。
拦截后执行 `popBackStackImmediate` 方法， 而 `popBackStack` 系列方法会调用 `popBackStackState` 构造 `records` 和 `isRecordPop` 列表，
`isRecordPop` 的内部元素的值均为 true 后续流程和提交事务是一样的， 根据 `isRecordPop` 值的不同选择执行 `executePopOps` 或 `executeOps` 方法。

## 怎样才会有多返回栈？

我们知道 FragmentManager 内部持有 `mBackStack list`，这对应着一个返回栈，
如果想要实现多返回栈，则需要多个 FragmentManager，而多 FragmentManager 则对应多个 fragment。

>用不同的宿主 fragment 的 独立的 `FragmentManager` 分别管理各自的返回栈。
单返回栈就很容易了，我们只需在同一个 FragmentManager 上添加返回栈即可。





