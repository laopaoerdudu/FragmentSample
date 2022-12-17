package com.wwe.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    val username = MutableLiveData("")

    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> = _headerText

    private val _showSnackBar = MutableLiveData<String>()
    val showSnackBar: LiveData<String> = _showSnackBar

    fun updateHeaderClick() {
        _headerText.postValue(username.value)
    }

    fun showSnackBarClick() {
        _showSnackBar.postValue(username.value)
    }
}