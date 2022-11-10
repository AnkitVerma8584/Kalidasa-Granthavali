package com.kalidasagranthavali.ass.util.translation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.coroutines.resume

class LanguageTranslator(
    private val languageIdentifier: LanguageIdentifier
) {

    suspend fun getLanguageCode(text: String): String? = languageIdentifier.identifyLanguage(text).await()

    private suspend fun makeModelText(
        text: String,
        from: String = TranslateLanguage.ENGLISH,
        to: String = Locale.getDefault().language
    ): String {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(from)
            .setTargetLanguage(to)
            .build()

        val translator = Translation.getClient(options)

        return translator.downloadModel(text)
    }

    private suspend fun Translator.downloadModel(text: String): String =
        suspendCancellableCoroutine { cont ->
            this.downloadModelIfNeeded().addOnSuccessListener {
                this.translate(text).addOnSuccessListener {
                    cont.resume(it)
                }.addOnFailureListener {
                    cont.cancel(it)
                }
            }.addOnFailureListener {
                cont.cancel(it)
            }
        }

}