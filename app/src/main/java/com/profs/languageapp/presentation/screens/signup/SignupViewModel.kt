package com.profs.languageapp.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val service: DomainService
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


    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onFirstNameChange(firstName: String) {
        _firstName.value = firstName
    }

    fun onLastNameChange(lastName: String) {
        _lastName.value = lastName
    }

    fun onPasswordStateChange(passwordState: Boolean) {
        _passwordState.value = passwordState
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

}