package com.profs.languageapp.presentation.screens.profile

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "settings")

@HiltViewModel
class ThemeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val dataStore = context.dataStore

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.data.collect { prefs ->
                _isDarkTheme.value = prefs[DARK_THEME_KEY] ?: false
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[DARK_THEME_KEY] = !_isDarkTheme.value
            }
            _isDarkTheme.value = !_isDarkTheme.value
        }
    }
}

