package com.kalidasagranthavali.ass.util.translation

import com.google.mlkit.nl.languageid.LanguageIdentifier
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LanguageTranslator(
    private val languageIdentifier: LanguageIdentifier
) {

    suspend fun String.getLanguageCode(): String? {
        val l = languageIdentifier.identifyLanguage(this)
        return suspendCoroutine { continuation ->
            l.addOnSuccessListener { code ->
                if (code == "und") {
                    continuation.resume(null)
                } else {
                    continuation.resume(code)
                }
            }.addOnFailureListener {
                continuation.resume(null)
            }
        }
    }

    /*  suspend fun String.translateText():String{
          val options = TranslatorOptions.Builder()
              .setSourceLanguage(TranslateLanguage.ENGLISH)
              .setTargetLanguage(TranslateLanguage.HINDI)
              .build()

          val englishHindiTranslator = Translation.getClient(options)

          val conditions = DownloadConditions.Builder()
              .build()
        //  englishHindiTranslator.downloadModelIfNeeded(conditions).
          *//* .addOnSuccessListener {
             // Model downloaded successfully. Okay to start translating.
             // (Set a flag, unhide the translation UI, etc.)
             englishGermanTranslator.translate(this)
                 .addOnSuccessListener { translatedText ->
                     // Translation successful.
                     //TODO send translatedText
                 }
                 .addOnFailureListener { exception ->
                     // Error.
                     // ...
                 }
         }
         .addOnFailureListener { exception ->
             // Model couldn’t be downloaded or other internal error.
             // ...
         }*//*
    }
*/


}