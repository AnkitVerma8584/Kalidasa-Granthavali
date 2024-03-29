package com.kalidasagranthavali.ass.util.locale

import android.content.Context
import com.kalidasagranthavali.ass.data.local.UserDataStore
import java.util.*

object LocaleHelper {

    internal fun onAttach(context: Context): Context {
        val lang = getLanguage()
        return setLocale(context, lang)
    }

    internal fun setLocale(context: Context, language: String): Context {
        UserDataStore.getInstance().saveLanguage(language)
        return updateResources(context, language)
    }

    internal fun getLanguage(): String {
        return UserDataStore.getInstance().getLanguageId()
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










