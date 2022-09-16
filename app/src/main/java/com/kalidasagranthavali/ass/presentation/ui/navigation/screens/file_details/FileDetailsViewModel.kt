package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel

class FileDetailsViewModel : ViewModel() {
    init {

    }

    fun getModifiedString(query: String, paragraph: String): AnnotatedString {
        val index = paragraph.indexOf(query, ignoreCase = true)
        return if (index == -1) {
            buildAnnotatedString { append(paragraph) }
        } else {
            buildAnnotatedString { append(query) } +
                    getModifiedString(
                        query,
                        paragraph.substring(index + query.length)
                    )
        }
    }
}