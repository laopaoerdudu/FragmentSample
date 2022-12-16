package com.wwe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    /** 后续 FragmentManager 在创建/恢复 fragment 时，会使用此 factory 创建实例 */
    private val fragmentFactory: FragmentFactory by lazy {
        HSFragmentFactory("Roman")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 注意 FragmentFactory 的设置必须在 super.onCreate 之前，因为当 Activity 进入重建路径时，会在 super.onCreate 中使用到它。
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment::class.java, null
            )
            .addToBackStack(null)
            .commit()
    }
}