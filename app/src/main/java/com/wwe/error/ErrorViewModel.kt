package com.wwe.error

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wwe.util.SlowRandomNumberGenerator

class ErrorViewModel : ViewModel() {
    //错误用法
    lateinit var errorRandomNumber: LiveData<Int>
    private val numberGenerator = SlowRandomNumberGenerator()

    fun onGetNumber() {
        errorRandomNumber = Transformations.map(numberGenerator.getNumber()) {
            it
        }
    }
}