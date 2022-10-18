package com.kalidasagranthavali.ass.di

import com.google.mlkit.nl.languageid.LanguageIdentification
import com.kalidasagranthavali.ass.util.translation.LanguageTranslator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslatorModule {

    @Provides
    @Singleton
    fun provideLanguageTranslator(): LanguageTranslator =
        LanguageTranslator(LanguageIdentification.getClient())

}