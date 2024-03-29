package com.kalidasagranthavali.ass.data.local

import android.content.Context
import android.content.SharedPreferences
import com.kalidasagranthavali.ass.MyApplication
import java.util.*

const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
private const val LANGUAGE_PREFERENCE = "com.kalidasagranthavali.ass.Kalidasa.Granthavali"

class UserDataStore(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    companion object {
        fun getInstance() = UserDataStore(MyApplication.appContext)
    }

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