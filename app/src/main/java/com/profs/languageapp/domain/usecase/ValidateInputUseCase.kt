package com.profs.languageapp.domain.usecase

import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    private val emailPattern =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun isEmailValid(email: String): Boolean {
        return emailPattern.matches(email)
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
        if (password == confirmation) return true else return false
    }
}