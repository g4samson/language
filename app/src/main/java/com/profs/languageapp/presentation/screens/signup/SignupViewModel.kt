package com.profs.languageapp.presentation.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.OpenPdfUseCase
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val service: DomainService,
    private val validateInputUseCase: ValidateInputUseCase,
    private val openPdfUseCase: OpenPdfUseCase
) : ViewModel() {
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName
    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _passwordState = MutableStateFlow(false)
    val passwordState: StateFlow<Boolean> = _passwordState

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword


    fun onFirstNameChange(firstName: String) {
        _firstName.value = firstName
    }

    fun onLastNameChange(lastName: String) {
        _lastName.value = lastName
    }

    fun onPasswordStateChange(passwordState: Boolean) {
        _passwordState.value = passwordState
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }


    private val _emailError = MutableStateFlow(false)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError = _passwordError.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
        _emailError.value = !validateInputUseCase.isEmailValid(email)
    }

    fun onPasswordChange(password: String) {
        _password.value = password
        _passwordError.value = !validateInputUseCase.isPasswordValid(password)
    }

    fun onRulesClick(context: Context) {
        openPdfUseCase.openPdf(
            context = context,
            fileName = "example.pdf"
        )
    }

}