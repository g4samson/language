package com.profs.languageapp.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: DomainService
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignIn(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            //val success = service.signIn(email = _email.value, password = _password.value)
            //onResult(success)
        }
        //Log.d("GAV", "${_email.value} || ${_password.value}")
    }

}