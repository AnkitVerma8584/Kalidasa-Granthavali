package com.kalidasagranthavali.ass.util.locale

import android.content.Context
import com.kalidasagranthavali.ass.data.local.UserDataStore
import java.util.*

class LocaleModule {

    internal fun onAttach(context: Context): Context {
        val lang = getLanguage(context)
        return setLocale(context, lang)
    }

    internal fun setLocale(context: Context, language: String): Context {
        UserDataStore.getInstance(context).saveLanguage(language)
        return updateResources(context, language)
    }

    internal fun getLanguage(context: Context): String {
        return UserDataStore.getInstance(context).getLanguageId()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}










