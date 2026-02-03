package com.profs.languageapp.presentation.screens.login

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.mapper.toUser
import com.profs.languageapp.data.model.NetworkResult
import com.profs.languageapp.data.utils.LanguagePreferences
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.SetUserUseCase
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private val Application.dataStore by preferencesDataStore("login_prefs")


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val service: DomainService,
    private val validateInputUseCase: ValidateInputUseCase,
    private val setUserUseCase: SetUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val dataStore = app.dataStore

    private val KEY_EMAIL = stringPreferencesKey("login_email")
    private val KEY_PASSWORD = stringPreferencesKey("login_password")


    val kEmail = dataStore.data.map { it[KEY_EMAIL] ?: "" }
        .stateIn(viewModelScope, Eagerly, "")
    val kPassword = dataStore.data.map { it[KEY_PASSWORD] ?: "" }
        .stateIn(viewModelScope, Eagerly, "")

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

    fun saveLanguage() {
        viewModelScope.launch {
            LanguagePreferences.saveLanguage(context, _selectedLanguage.value)
            LanguagePreferences.setLanguage(context, _selectedLanguage.value)
        }
    }

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    fun saveLogin(kEmail: String, kPassword: String) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_EMAIL] = kEmail
                prefs[KEY_PASSWORD] = kPassword
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = service.loginUser(email.value, password.value)

            when (result) {
                is NetworkResult.Success -> {
                    saveLogin(email.value, password.value)
                    setUserUseCase(result.data.toUser())
                    _uiState.value = LoginUiState.Success
                }

                is NetworkResult.NoInternet -> {
                    _uiState.value = LoginUiState.NoInternet
                }

                is NetworkResult.ServerError -> {
                    _uiState.value = LoginUiState.ServerError(result.message)
                }
            }
        }
    }

}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    object NoInternet : LoginUiState()
    data class ServerError(val message: String) : LoginUiState()
}