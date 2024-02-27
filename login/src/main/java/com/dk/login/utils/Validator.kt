package com.dk.login.utils

class Validator {
    fun check(text: String, validatorType: ValidatorType): Boolean {
        return when (validatorType) {
            ValidatorType.NAME -> text.all { it in 'А'..'я' } && text.isNotEmpty()

            ValidatorType.PHONE -> text.length == 16
        }
    }
}