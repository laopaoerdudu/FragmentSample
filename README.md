当系统发生 Configuration Change 时（例如横竖屏旋转等）Fragment 会恢复重建，此时系统不知道该选择哪个构造函数，
所以系统与开发者约定，统一使用默认的空参构造函数构建，然后通过 `setArgments` 设置初始化值。

Fragment 恢复重建过程中，系统会调用静态方法 `Fragment.instantiate`（在 `onCreate` 和 `onActivityCreated` 之间）

## Fragment( contentLayoutId ) + FragmentFactory

**使用构造函数设置LayoutId 就无需重写 `onCreateView` 了。**

```
@Nullable
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
    if (mContentLayoutId != 0) {
        return inflater.inflate(mContentLayoutId, container, false);
    }
    return null;
}
```


你也许会问这跟 FragmentFactory 有什么关系呢?

因为使用了构造函数设置 mContentLayoutId，当 ConfigurationChange 发生时，默认调用无参构造函数进行 fragment 的恢复重建，
mContentLayoutId 信息会丢失，onCreateView 无法正常创建视图。

因此当使用构造函数设置 LayoutId 时，如果要考虑恢复重建的场景，**必须** 配套设置一个 `FragmentFactory`。可能是踩坑的人太多了，
在 1.1.0 之后的 doc 注释中特别强调了这一点:

>You must set a custom FragmentFactory 
if you want to use a non-default constructor to ensure that your constructor is called when the fragment is re-instantiated.

**总结：**

相对与以往 setArguments 传参方式，FragmentFactory 中允许开发者直接通过构造函数传参、创建 Fragment。
灵活的构造函数带来很多潜在收益，比如通过构造函数设置 LayoutId 可以为 Fragment 提供动态化的布局能力。
此外，FragmentFactory 在 dagger、koin 等DI框架的使用场景中也能发挥很大的作用。