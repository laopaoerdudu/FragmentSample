package com.wwe

import kotlinx.coroutines.*
import org.junit.Test

class CoroutineTest {

    @Test
    fun test() = runBlocking {
        repeat(100_000) {
            launch {
                var resource: Resource? = null // Not acquired yet
                try {
                    withTimeout(60) {
                        delay(50)
                        resource = Resource()
                    }
                } finally {
                    resource?.close() // Release the resource if it was acquired
                }
            }
        }
        println(acquired)
    }
}

var acquired = 0

class Resource {
    init {
        acquired++
    } // Acquire the resource

    fun close() {
        acquired--
    } // Release the resource
}