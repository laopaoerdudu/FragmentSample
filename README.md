##  利用 Fragment Result API 进行 Fragment 之间的通信

那么 Fragment 间通信的新 API 给我们带来哪些好处呢：

- 在 Fragment 之间传递数据，不会持有对方的引用

- 当生命周期处于 ON_START 时开始处理数据，避免当 Fragment 处于不可预知状态的时，可能发生未知的问题

- 当生命周期处于 ON_DESTROY 时，移除监听


**`setReorderingAllowed(true)`**

>This method is actually optional, however, the documentation strongly recommends that we add it. 
>This method enables some optimizations when multiple transactions execute together. 
>Also, if any fragments are added and then immediately replaced, 
> this method makes sure that they do not go through any lifecycle methods.