package com.kalidasagranthavali.ass.di

import com.kalidasagranthavali.ass.util.locale.LocaleModule
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
