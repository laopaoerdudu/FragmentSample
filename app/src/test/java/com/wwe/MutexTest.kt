package com.wwe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * 其实如果多个协程共用一个线程，那么它们之间也就没有什么线程并发的问题了。
 * 此外，协程不会阻塞线程，它们反而会挂起自己的工作，因而更加有效。
 *
 * 在协程中请谨慎使用 Java 语言中的同步类，因为它们会阻塞整个协程所处的线程，并且引发 活跃度 问题。
 * 使用 Mutex 的协程在可以继续执行之前会挂起操作，因此要比 Java 编程语言中的 lock 高效很多，因为后者会阻塞整个线程
 */
class MutexTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test() = runBlocking {
        val mutex = Mutex()
        var count = 0
        repeat(1000) {
            GlobalScope.launch {
                mutex.withLock {
                    count++
                }
            }
        }
        println("count: $count")
    }

    @Test
    fun test1() = runTest {
        val viewModel = HomeViewModel()
        viewModel.loadMessage()
        advanceUntilIdle()
        Assert.assertEquals("Greetings!", viewModel.message.value)
    }

    @Test
    fun test2() = runTest {
        val viewModel = HomeViewModel()
        viewModel.loadData()
        advanceUntilIdle()
    }

    class HomeViewModel : ViewModel() {
        private val _message = MutableStateFlow("")
        val message: StateFlow<String> get() = _message

        fun loadMessage() {
            viewModelScope.launch {
                println("1:" + Thread.currentThread().name)
                delay(1000)
                _message.value = "Greetings!"
                println("2:" + Thread.currentThread().name)
            }
        }

        fun loadData() {
            viewModelScope.launch(Dispatchers.Main) {
                print("1:" + Thread.currentThread().name)
                withContext(Dispatchers.IO) {
                    delay(1000)
                    print("2:" + Thread.currentThread().name)
                }
                print("3:" + Thread.currentThread().name)
            }
        }
    }
}