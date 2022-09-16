package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.R
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar

@Composable
fun FileDetailsPage() {
    val text = stringResource(id = R.string.demo)

    var query by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(query = query, onSearchQueryChanged = {
            query = it
        })

        var start = 0
        val boldSpanStyle =
            SpanStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold,
                background = MaterialTheme.colorScheme.primaryContainer
            )

        Text(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            text = buildAnnotatedString {
                while (text.indexOf(query, start, ignoreCase = true) != -1 && query.isNotBlank()) {
                    val firstIndex = text.indexOf(query, start, true)
                    val end = firstIndex + query.length
                    append(text.substring(start, firstIndex))
                    withStyle(style = boldSpanStyle) {
                        append(text.substring(firstIndex, end))
                    }
                    start = end
                }
                append(text.substring(start, text.length))
                toAnnotatedString()
            })
    }


}