package com.wwe.sharedflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class SharedFlowViewModel : ViewModel() {
    val username = MutableStateFlow("")

    private val _headerText = MutableSharedFlow<String>()
    val headerText: SharedFlow<String> = _headerText

    private val _showSnackBar = MutableSharedFlow<String>()
    val showSnackBar: SharedFlow<String> = _showSnackBar

    fun updateHeaderClick() {
        _headerText.tryEmit(username.value)
    }

    fun showSnackbarClick() {
        _showSnackBar.tryEmit(username.value)
    }
}