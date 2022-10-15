package com.kalidasagranthavali.ass

import android.app.Application
import android.content.Context
import com.kalidasagranthavali.ass.util.locale.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }
}