##  利用 Fragment Result API 进行 Fragment 之间的通信

### Use case one

如果在 FragmentA 中接受 FragmentB 发送的数据，FragmentA 和 FragmentB 处于相同的层级，
则通过 parent FragmentManager 进行通信，FragmentA 必须使用 parent FragmentManager 注册 listener。

FragmentB 发送数据到 FragmentA

```
parentFragmentManager.setFragmentResult(
    requestKey, // Same request key FragmentA used to register its listener
    bundleOf(key to value) // The data to be passed to FragmentA
)
```

FragmentA 接收数据

```
parentFragmentManager.setFragmentResultListener(
            KeyConstant.KEY_SELECTED_COUNTRY, viewLifecycleOwner
        ) { _, bundle ->
            view.findViewById<TextView>(R.id.tvContent).text = bundle.getString("name")
        }
```

### Use case two

如果在 FragmentA 中接收 FragmentB 发送的数据，FragmentA 是 FragmentB 的父容器，则 FragmentA 中代码应该这么写：

```
childFragmentManager.setFragmentResultListener(...)
```

**总结：**

数据接收：

```
FragmentManager.setFragmentResultListener(
    requestKey,
    lifecycleOwner,
    FragmentResultListener { requestKey: String, result: Bundle ->
        // Handle result
    })
```

数据发送：

```
parentFragmentManager.setFragmentResult(
    requestKey, // Same request key FragmentA used to register its listener
    bundleOf(key to value) // The data to be passed to FragmentA
)
```

那么 Fragment 间通信的新 API 给我们带来哪些好处呢：

- 在 Fragment 之间传递数据，不会持有对方的引用

- 当生命周期处于 ON_START 时开始处理数据，避免当 Fragment 处于不可预知状态的时，可能发生未知的问题

- 当生命周期处于 ON_DESTROY 时，移除监听


**`setReorderingAllowed(true)`**

>This method is actually optional, however, the documentation strongly recommends that we add it. 
>This method enables some optimizations when multiple transactions execute together. 
>Also, if any fragments are added and then immediately replaced, 
> this method makes sure that they do not go through any lifecycle methods.