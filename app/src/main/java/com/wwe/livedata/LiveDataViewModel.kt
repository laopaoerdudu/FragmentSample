package com.wwe.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> = _headerText

    private val _showSnackBar = MutableLiveData<String>()
    val showSnackBar: LiveData<String> = _showSnackBar

    fun updateHeaderClick() {
        _headerText.postValue(userName.value)
    }

    fun showSnackBarClick() {
        _showSnackBar.postValue(userName.value)
    }

    fun updateUserName(name: String) {
        _userName.postValue(name)
    }
}