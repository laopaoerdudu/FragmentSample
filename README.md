##  利用 Fragment Result API 进行 Fragment 之间的通信

如果在 FragmentA 中接受 FragmentB 发送的数据，FragmentA 和 FragmentB 处于相同的层级，
则通过 parent FragmentManager 进行通信，FragmentA 必须使用 parent FragmentManager 注册 listener。

FragmentB 发送数据到 FragmentA

```
parentFragmentManager.setFragmentResult(
    requestKey, // Same request key FragmentA used to register its listener
    bundleOf(key to value) // The data to be passed to FragmentA
)
```

**`setReorderingAllowed(true)`**

>This method is actually optional, however, the documentation strongly recommends that we add it. 
>This method enables some optimizations when multiple transactions execute together. 
>Also, if any fragments are added and then immediately replaced, 
> this method makes sure that they do not go through any lifecycle methods.