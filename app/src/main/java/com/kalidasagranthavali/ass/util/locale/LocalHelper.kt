package com.kalidasagranthavali.ass.util.locale

import android.content.Context
import java.util.*

object LocalHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val LANGUAGE_PREFERENCE = "com.kalidasagranthavali.ass.Kalidasa.Granthavali"

    internal fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language) ?: "en"
        return setLocale(context, lang)
    }

    internal fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return updateResources(context, language)
    }

    internal fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = context.getSharedPreferences(LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = context.getSharedPreferences(LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}

















