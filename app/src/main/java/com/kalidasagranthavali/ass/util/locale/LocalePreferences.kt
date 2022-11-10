package com.kalidasagranthavali.ass.util.locale

import android.content.Context
import android.content.SharedPreferences
import java.util.*

private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
const val LANGUAGE_PREFERENCE = "com.kalidasagranthavali.ass.Kalidasa.Granthavali"

class LocalePreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    internal fun saveLanguage(languageId: String) {
        editor.putString(SELECTED_LANGUAGE, languageId)
        editor.apply()
    }

    internal fun getLanguageId(): String =
        preferences.getString(SELECTED_LANGUAGE, Locale.getDefault().language)
            ?: Locale.getDefault().language

    internal fun register(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    internal fun unregister(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}