package com.wwe

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val btnActivityResult: Button by lazy {
        findViewById(R.id.btnActivityResult)
    }

    private val btnRequestPermissions: Button by lazy {
        findViewById(R.id.btnRequestPermissions)
    }

    private val btnUsingCustomContract: Button by lazy {
        findViewById(R.id.btnUsingCustomContract)
    }

    private val tvResult: TextView by lazy {
        findViewById(R.id.tvResult)
    }

    private val requestDataLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                tvResult.text = result.data?.getStringExtra("data").orEmpty()
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // TODO: user allow the permission.
            } else {
                // TODO: user deny the permission.
            }
        }

    private val getDataLauncher = registerForActivityResult(GetDataContract()) { data ->
        // Handle data from SecondActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnActivityResult.setOnClickListener {
            requestDataLauncher.launch(Intent(this@MainActivity, SecondActivity::class.java))
        }
        btnRequestPermissions.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        btnUsingCustomContract.setOnClickListener {
            getDataLauncher.launch(null)
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