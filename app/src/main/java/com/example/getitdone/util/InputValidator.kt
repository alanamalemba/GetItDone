package com.example.getitdone.util

object InputValidator {
    fun checkIsInputValid(editable: String?):Boolean {
        return !editable?.trim().isNullOrEmpty() && editable!!.trim().length > 1
    }
}