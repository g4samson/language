package com.profs.languageapp.presentation.screens.languageSelect

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.source.LocalDataSource
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageSelectViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    private val _languageList = MutableStateFlow<List<Language>>(emptyList())
    val languageList: StateFlow<List<Language>> = _languageList.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    init {
        getLanguages()
    }

    private fun getLanguages() {
        _languageList.value = localDataSource.getLanguages()
    }

    fun onLanguageChange(langCode: String) {
        _selectedLanguage.value = langCode
    }

    fun saveLanguage() {
        viewModelScope.launch {
            Constants.saveLanguage(context, _selectedLanguage.value)
            Constants.setLanguage(context, _selectedLanguage.value)
        }
    }
}