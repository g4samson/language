package com.profs.languageapp.domain.usecase

import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    private val emailPattern =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun isEmailValid(email: String): Boolean {
        return emailPattern.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}