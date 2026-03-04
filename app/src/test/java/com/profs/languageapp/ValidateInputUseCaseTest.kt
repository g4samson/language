package com.profs.languageapp

import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidateInputUseCaseTest {

    private lateinit var validateInputUseCase: ValidateInputUseCase

    @Before
    fun setup() {
        validateInputUseCase = ValidateInputUseCase()
    }

    @Test
    fun `valid email`() {
        val email = "test@example.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `email with subdomain`() {
        val email = "user@mail.example.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `email with plus`() {
        val email = "user+test@gmail.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `email without at symbol`() {
        val email = "testexample.com"
        assertFalse(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `email without domain`() {
        val email = "test@"
        assertFalse(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `email with spaces trimmed`() {
        val email = "   test@example.com   "
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun `empty email`() {
        val email = ""
        assertFalse(validateInputUseCase.isEmailValid(email))
    }
}