package com.example.getitdone

import com.example.getitdone.util.InputValidator
import org.junit.Assert.*
import org.junit.Test

class InputValidationTest {
    @Test
    fun inputValidator_returnsFalseWhenEmpty() {

        val result = InputValidator.checkIsInputValid("")

        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenNull() {

        val result = InputValidator.checkIsInputValid(null)

        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyWhiteSpace() {

        val result = InputValidator.checkIsInputValid("  ")

        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenMoreThanOneWhiteSpaceCharacter() {

        val result = InputValidator.checkIsInputValid("More than one ...")

        assertTrue(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenTwoNoneWhiteSpaceCharacters() {

        val result = InputValidator.checkIsInputValid("   ww")

        assertTrue(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyOneNoneWhiteSpaceCharacter() {

        val result = InputValidator.checkIsInputValid("   e")

        assertFalse(result)
    }
}