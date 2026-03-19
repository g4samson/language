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
    fun validEmail() {
        // Проверка обычного корректного email
        val email = "test@example.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emailWithSubdomain() {
        // Проверка email с поддоменом
        val email = "user@mail.example.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emailWithPlus() {
        // Проверка email с символом "+"
        val email = "user+test@gmail.com"
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emailWithoutAtSymbol() {
        // Email без символа @ должен быть невалидным
        val email = "testexample.com"
        assertFalse(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emailWithoutDomain() {
        // Email без домена должен быть невалидным
        val email = "test@"
        assertFalse(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emailWithSpacesTrimmed() {
        // Пробелы по краям должны игнорироваться (trim)
        val email = "   test@example.com   "
        assertTrue(validateInputUseCase.isEmailValid(email))
    }

    @Test
    fun emptyEmail() {
        // Пустая строка должна быть невалидной
        val email = ""
        assertFalse(validateInputUseCase.isEmailValid(email))
    }
}