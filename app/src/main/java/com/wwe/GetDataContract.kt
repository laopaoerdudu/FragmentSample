package com.wwe

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

// Custom contract
class GetDataContract : ActivityResultContract<Void, String?>() {
    override fun createIntent(context: Context, input: Void): Intent {
        return Intent(context, SecondActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode == Activity.RESULT_OK) {
            intent?.let {
                return it.getStringExtra("data")
            }
        }
        return null
    }
}