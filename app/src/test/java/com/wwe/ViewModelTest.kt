package com.wwe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test() = runTest {
        // GIVEN
        val viewModel = MyViewModel()

        // WHEN
        viewModel.setNewValue("foo")

        // THEN
        assertEquals(viewModel.liveData.value, "foo") // Passes
        assertEquals(viewModel.mapLiveData.value, "FOO") // Fails!
    }

    @Test
    fun test1() = runTest {
        // GIVEN
        val viewModel = MyViewModel()

        // WHEN
        viewModel.setNewValue("foo")

        // THEN
        assertEquals(viewModel.liveData.getOrAwaitValue(), "foo") // Passes
        assertEquals(viewModel.mapLiveData.getOrAwaitValue(), "FOO") // Pass
    }
}

class MyViewModel : ViewModel() {
    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    // [Transformations.map] on liveData1 that converts the value to uppercase:
    val mapLiveData = liveData.map { it.uppercase() }

    fun setNewValue(newValue: String) {
        _liveData.value = newValue
    }
}