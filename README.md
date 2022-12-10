当系统发生 Configuration Change 时（例如横竖屏旋转等）Fragment 会恢复重建，此时系统不知道该选择哪个构造函数，
所以系统与开发者约定，统一使用默认的空参构造函数构建，然后通过 `setArgments` 设置初始化值。

