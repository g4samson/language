package com.profs.languageapp.presentation.screens.languageSelect

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.model.Language
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.data.utils.LanguagePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: Constants
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
            LanguagePreferences.saveLanguage(context, _selectedLanguage.value)
            LanguagePreferences.setLanguage(context, _selectedLanguage.value)
        }
    }
}