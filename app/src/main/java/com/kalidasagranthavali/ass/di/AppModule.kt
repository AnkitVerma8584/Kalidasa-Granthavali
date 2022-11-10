package com.kalidasagranthavali.ass.di

import android.app.Application
import com.kalidasagranthavali.ass.util.locale.LocaleModule
import com.kalidasagranthavali.ass.util.locale.LocalePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocaleModule(): LocaleModule = LocaleModule()
}
