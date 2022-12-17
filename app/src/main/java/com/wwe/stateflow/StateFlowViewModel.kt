package com.wwe.stateflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateFlowViewModel : ViewModel() {
    val userName = MutableStateFlow("")

    private val _headerText = MutableStateFlow("Header")
    val headerText = _headerText.asStateFlow()

    private val _showSnackbar = MutableStateFlow<String>("default")
    val showSnackBar: StateFlow<String> = _showSnackbar


    fun updateHeaderClick() {
        _headerText.value = userName.value
    }

    fun showSnackBarClick() {
        _showSnackbar.value = userName.value
    }
}