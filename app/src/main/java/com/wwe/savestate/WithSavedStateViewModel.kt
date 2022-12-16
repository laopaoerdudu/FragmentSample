package com.wwe.savestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * 带 SavedState 的 ViewModel 进程杀死后数据不丢失
 */
class WithSavedStateViewModel(private val state: SavedStateHandle) : ViewModel() {
    fun setValue(value: String) = state.set("key", value)
    fun getValue(): LiveData<String> = state.getLiveData("key")
}