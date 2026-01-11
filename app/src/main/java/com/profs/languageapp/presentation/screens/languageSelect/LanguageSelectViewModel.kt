package com.profs.languageapp.presentation.screens.languageSelect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectViewModel @Inject constructor(
    private val service: DomainService
) : ViewModel() {

    private val _languageList = MutableStateFlow<List<Language>?>(listOf())
    val languageList: Flow<List<Language>?> = _languageList

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language

    fun onLanguageChange(language: String) {
        _language.value = language
    }

    init {
        getLanguages()
    }

    fun getLanguages() {
        viewModelScope.launch {
            val languages = service.getAvailableLanguages()
            _languageList.emit(languages.map { it })
        }
    }
}