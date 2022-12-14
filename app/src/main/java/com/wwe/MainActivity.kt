package com.wwe

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory


class MainActivity : AppCompatActivity() {

    private val textViewFragmentCount: TextView by lazy {
        findViewById(R.id.textViewFragmentCount)
    }

    private val buttonAddFragment: Button by lazy {
        findViewById(R.id.buttonAddFragment)
    }

    /** 后续 FragmentManager 在创建/恢复 fragment 时，会使用此 factory 创建实例 */
    private val fragmentFactory: FragmentFactory by lazy {
        HSFragmentFactory("Roman")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 注意 FragmentFactory 的设置必须在 super.onCreate 之前，因为当 Activity 进入重建路径时，会在 super.onCreate 中使用到它。
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment::class.java, null
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("WWE", "MainActivity onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("WWE", "MainActivity onRestoreInstanceState")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.i("WWE", "MainActivity onConfigurationChanged landscape")
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.i("WWE", "MainActivity onConfigurationChanged portrait")
            }
            else -> {
                // Do nothing
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("WWE", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("WWE", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("WWE", "MainActivity onDestroy")
    }
}