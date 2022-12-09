package com.wwe

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, CountriesFragment::class.java, null)
                .commit()
        }
        supportFragmentManager.setFragmentResultListener(
            KeyConstant.KEY_SELECTED_COUNTRY, this
        ) { _, bundle ->
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, DetailFragment::class.java, bundle)
                .commit()
        }
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