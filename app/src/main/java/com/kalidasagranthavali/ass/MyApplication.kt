package com.kalidasagranthavali.ass

import android.app.Application
import android.content.Context
import com.kalidasagranthavali.ass.util.locale.LocalHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocalHelper.onAttach(newBase!!))
    }

}