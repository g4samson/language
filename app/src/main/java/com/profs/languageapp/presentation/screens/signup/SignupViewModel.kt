package com.profs.languageapp.presentation.screens.signup

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.OpenPdfUseCase
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

private val Application.dataStore by preferencesDataStore("signup_prefs")

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val app: Application,
    private val service: DomainService,
    private val validateInputUseCase: ValidateInputUseCase,
    private val openPdfUseCase: OpenPdfUseCase,
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName
    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val dataStore = app.dataStore
    private val KEY_PAGE = intPreferencesKey("signup_page")
    private val KEY_FIRST_NAME = stringPreferencesKey("signup_firstName")
    private val KEY_LAST_NAME = stringPreferencesKey("signup_lastName")
    private val KEY_EMAIL = stringPreferencesKey("signup_email")

    private val _passwordState = MutableStateFlow(false)
    val passwordState: StateFlow<Boolean> = _passwordState

    fun onPasswordStateChange(passwordState: Boolean) {
        _passwordState.value = passwordState
    }

    val page = dataStore.data.map { it[KEY_PAGE] ?: 0 }
        .stateIn(viewModelScope, Eagerly, 0)

    val kFName = dataStore.data.map { it[KEY_FIRST_NAME] ?: "" }
        .stateIn(viewModelScope, Eagerly, "")
    val kLName = dataStore.data.map { it[KEY_LAST_NAME] ?: "" }
        .stateIn(viewModelScope, Eagerly, "")
    val kEM = dataStore.data.map { it[KEY_EMAIL] ?: "" }
        .stateIn(viewModelScope, Eagerly, "")

    fun saveCurrentPage(newPage: Int) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_PAGE] = newPage
            }
        }
    }

    fun saveTextFields(kFName: String, kLName: String, kEM: String) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_FIRST_NAME] = kFName
                prefs[KEY_LAST_NAME] = kLName
                prefs[KEY_EMAIL] = kEM
            }
        }
    }

    fun loadTextFields() {
        viewModelScope.launch {
            _firstName.value = kFName.value
            _lastName.value = kLName.value
            _email.value = kEM.value
        }
    }

    init {
        loadTextFields()
    }

    fun onFirstNameChange(firstName: String) {
        _firstName.value = firstName
    }

    fun onLastNameChange(lastName: String) {
        _lastName.value = lastName
    }

    private val _emailError = MutableStateFlow(false)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError = _passwordError.asStateFlow()

    private val _conError = MutableStateFlow(false)
    val conError = _conError.asStateFlow()

    fun onConfirmPasswordChange(confirmPassword: String, password: String) {
        _confirmPassword.value = confirmPassword
        _conError.value = !validateInputUseCase.confirmedPassword(confirmPassword, password)
    }

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

    fun registerUser(email: String, firstName: String, lastName: String, password: String) {
        viewModelScope.launch {
            val response = service.registerUser(
                email = email,
                firstName = firstName,
                lastName = lastName,
                languageCode = "en",
                password = password
            )
            if (response != null) {
                Log.i("WW", "User registered: ${response.email}")
            } else {
                Log.e("WW", "FAILED registered")
            }
        }
    }

}