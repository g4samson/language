package com.profs.languageapp.domain.usecase

import android.util.Log
import android.util.Patterns
import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
        Log.d("EMAIL", ">$email< length=${email.length}")
        Log.d("EMAIL", "${Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()}")
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