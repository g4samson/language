package com.profs.languageapp.domain.usecase

import android.util.Log
import android.util.Patterns
import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")


    fun isEmailValid(email: String): Boolean {
        return false
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpace = password.any { it == ' ' }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() && it != ' ' }

        return hasUpperCase &&
                hasLowerCase &&
                hasDigit &&
                hasSpace &&
                hasSpecialChar
    }

    fun confirmedPassword(password: String, confirmation: String) : Boolean {
        return password == confirmation
    }
}