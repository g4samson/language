package com.profs.languageapp.presentation.screens.onboarding

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.service.DomainServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private val Application.dataStore by preferencesDataStore("onboarding_prefs")

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val app: Application,
    private val service: DomainServiceImpl
) : AndroidViewModel(app) {

    val pages = service.getOnboardingPages()
    private val dataStore = app.dataStore

    private val KEY_PAGE = intPreferencesKey("onboarding_page")
    private val KEY_COMPLETED = booleanPreferencesKey("onboarding_completed")

    val page = dataStore.data.map { it[KEY_PAGE] ?: 0 }
        .stateIn(viewModelScope, Eagerly, 0)

    val completed = dataStore.data.map { it[KEY_COMPLETED] ?: false }
        .stateIn(viewModelScope, Eagerly, false)

    fun saveCurrentPage(newPage: Int) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_PAGE] = newPage
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_COMPLETED] = true
            }
        }
    }
}