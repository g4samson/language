package com.profs.languageapp.data.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.languageDataStore by preferencesDataStore(name = "language_prefs")
object Constants {
    private val LANGUAGE_KEY = stringPreferencesKey("language")

    fun getLanguage(context: Context): Flow<String?> =
        context.languageDataStore.data.map { prefs ->
            val lang = prefs[LANGUAGE_KEY]
            Log.d("LANG_TEST", "Read language = $lang")
            lang
        }

    suspend fun saveLanguage(context: Context, lang: String) {
        context.languageDataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = lang
        }
        Log.d("LANG_TEST", "Saved language = $lang")
    }

    fun setLanguage(lang: String) {
        val locale = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(locale)
        Log.d("LANG_TEST", "Locale applied = $lang")
    }
}