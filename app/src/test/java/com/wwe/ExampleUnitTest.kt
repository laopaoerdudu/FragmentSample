package com.wwe

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    suspend fun fetchData(): String {
        delay(1000L)
        return "Hello world"
    }

    @Test
    fun dataShouldBeHelloWorld() = runTest {
        val data = fetchData()
        assertEquals("Hello world", data)
    }

    private inline fun crossInlinePOC(crossinline action: () -> Int) {
        action()
    }

    @Test
    fun test_CrossInline() {
        crossInlinePOC {
            println("Hello crossInline")
            // return // return is not allowed here
            return@crossInlinePOC 3
        }
        println("CrossInline end")
    }
}