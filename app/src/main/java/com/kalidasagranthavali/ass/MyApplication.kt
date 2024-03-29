package com.kalidasagranthavali.ass

import android.app.Application
import android.content.Context
import com.kalidasagranthavali.ass.util.locale.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun attachBaseContext(newBase: Context?) {
        appContext = newBase!!
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
}