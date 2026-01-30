package com.profs.languageapp.presentation.screens.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.User
import com.profs.languageapp.data.repository.UserRepository
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: DomainService,
    private val validateInputUseCase: ValidateInputUseCase,
    private val repository: UserRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

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

    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    fun saveLanguage() {
        viewModelScope.launch {
            Constants.saveLanguage(context, _selectedLanguage.value)
            Constants.setLanguage(context, _selectedLanguage.value)
        }
    }


    suspend fun loginUser(email: String, password: String): Boolean {
        val response = service.loginUser(email, password)
        return if (response != null) {
            repository.setUser(User(
                id = response.id,
                email = response.email,
                firstName = response.firstName,
                lastName = response.lastName,
                languageCode = response.languageCode,
                rating = response.rating,
                image = response.image
            ))
            _selectedLanguage.value = response.languageCode
            saveLanguage()
            true
        } else false
    }

}