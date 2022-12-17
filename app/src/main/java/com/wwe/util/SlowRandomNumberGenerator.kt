package com.wwe.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.concurrent.Executors

class SlowRandomNumberGenerator {
    private val random = Random()

    fun getNumber(): LiveData<Int> {
        val result = MutableLiveData<Int>()

        // Send a random number after a while
        Executors.newSingleThreadExecutor().execute {
            Log.i("WWE", "Thread: ${Thread.currentThread().name}")
            Thread.sleep(500)
            result.postValue(random.nextInt(1000))
        }
        return result
    }
}