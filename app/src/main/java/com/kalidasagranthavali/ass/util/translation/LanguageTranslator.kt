package com.kalidasagranthavali.ass.util.translation

import android.content.SharedPreferences
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LanguageTranslator(
    private val languageIdentifier: LanguageIdentifier
) {
    suspend fun getLanguageCode(text: String): String? =
        languageIdentifier.identifyLanguage(text).await()

    private suspend fun makeModelText(text: String, from: String, to: String): String {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(from)
            .setTargetLanguage(to)
            .build()
        val translator = Translation.getClient(options)
        return translator.downloadModel(text)
    }

    private suspend fun Translator.downloadModel(text: String): String = suspendCoroutine { cont ->
        this.downloadModelIfNeeded().addOnSuccessListener {
            this.translate(text).addOnSuccessListener {
                cont.resume(it)
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }

}