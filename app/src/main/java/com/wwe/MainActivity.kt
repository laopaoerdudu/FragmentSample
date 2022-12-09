package com.wwe

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val textViewFragmentCount: TextView by lazy {
        findViewById(R.id.textViewFragmentCount)
    }

    private val buttonAddFragment: Button by lazy {
        findViewById(R.id.buttonAddFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewFragmentCount.text =
            "Fragment count in back stack: ${supportFragmentManager.backStackEntryCount}"
        supportFragmentManager.addOnBackStackChangedListener {
            textViewFragmentCount.text =
                "Fragment count in back stack: ${supportFragmentManager.backStackEntryCount}"
            val backstackEntryMessage =
                StringBuilder("Current status of fragment transaction back stack: ${supportFragmentManager.backStackEntryCount} \n")
            for (i in supportFragmentManager.backStackEntryCount - 1 downTo 0) {
                backstackEntryMessage.append("${supportFragmentManager.getBackStackEntryAt(i).name} \n")
            }
            Log.i("WWE", backstackEntryMessage.toString())
        }
        buttonAddFragment.setOnClickListener {
            var fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            fragment = when (fragment) {
                is FragmentOne -> {
                    FragmentTwo()
                }
                is FragmentTwo -> {
                    FragmentThree()
                }
                is FragmentThree -> {
                    FragmentOne()
                }
                else -> {
                    FragmentOne()
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, "TargetFragment")
                .addToBackStack("Replace $fragment")
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