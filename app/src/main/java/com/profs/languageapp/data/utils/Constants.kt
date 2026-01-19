package com.profs.languageapp.data.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.languageDataStore by preferencesDataStore(name = "language_prefs")

object Constants {

    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val LANGUAGE_SELECTED_KEY = booleanPreferencesKey("language_selected")

    fun getLanguage(context: Context): Flow<String?> =
        context.languageDataStore.data.map { it[LANGUAGE_KEY] }

    fun isLanguageSelected(context: Context): Flow<Boolean> =
        context.languageDataStore.data.map {
            it[LANGUAGE_SELECTED_KEY] ?: false
        }

    suspend fun saveLanguage(context: Context, lang: String) {
        context.languageDataStore.edit {
            it[LANGUAGE_KEY] = lang
            it[LANGUAGE_SELECTED_KEY] = true
        }
    }

    fun setLanguage(context: Context, lang: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(lang)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
            )
        }
    }
}