package com.profs.languageapp.presentation.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.mapper.toUser
import com.profs.languageapp.data.utils.LanguagePreferences
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.SetUserUseCase
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
    private val setUserUseCase: SetUserUseCase,
    @ApplicationContext private val context: Context
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
            LanguagePreferences.saveLanguage(context, _selectedLanguage.value)
            LanguagePreferences.setLanguage(context, _selectedLanguage.value)
        }
    }


    suspend fun loginUser(email: String, password: String): Boolean {
        val response = service.loginUser(email, password)
        return if (response != null) {
            setUserUseCase(response.toUser())

            _selectedLanguage.value = response.languageCode
            saveLanguage()
            true
        } else false
    }

}