package com.example.getitdone

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

    @Test
    fun subtraction_isCorrect() {
        val result = 7 - 2

        assertEquals(5, result)
    }

    @Test
    fun greeter_greetsPersonCorrectly() {
        val greeter = Greeter()

        val result = greeter.greet("Alan")

        assertEquals("Hello, Alan!", result)
    }
}

class Greeter() {
    fun greet(name: String): String {
        return "Hello, $name!"
    }
}