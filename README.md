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

**`setReorderingAllowed(true)`**

>This method is actually optional, however, the documentation strongly recommends that we add it. 
>This method enables some optimizations when multiple transactions execute together. 
>Also, if any fragments are added and then immediately replaced, 
> this method makes sure that they do not go through any lifecycle methods.