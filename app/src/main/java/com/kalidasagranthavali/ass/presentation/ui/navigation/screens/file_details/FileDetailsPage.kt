package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar

@Composable
fun FileDetailsPage(
    viewModel: FileDetailsViewModel = hiltViewModel()
) {
    val text by viewModel.text.collectAsState()
    val state by viewModel.fileState.collectAsState()
    val query by viewModel.fileDataQuery.collectAsState()

    var scale by remember { mutableStateOf(16f) }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query, onSearchQueryChanged = { viewModel.updateQuery(it) },
            onClearPressed = { viewModel.updateQuery() }, hint = "Search for any text..."
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        if ((scale * zoom) in 11.0f..60.0f) scale *= zoom
                    }
                }
        ) {
            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error?.let {
                Text(
                    text = it.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } ?: DocumentContent(
                text = text,
                scale = scale,
                query = query
            )
        }
    }
}

@Composable
private fun BoxScope.DocumentContent(
    text: List<String?>,
    query: String,
    scale: Float
) {
    if (text.isEmpty())
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    else {
        LazyColumn {
            items(text) { item ->
                DocumentText(query = query, text = item, scale = scale)
            }
        }
    }
}

@Composable
private fun DocumentText(
    query: String,
    text: String?,
    scale: Float,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.SemiBold,
        background = MaterialTheme.colorScheme.primaryContainer
    )
) {
    text?.let {
        val annotatedString by remember(query) {
            derivedStateOf {
                buildAnnotatedString {
                    var start = 0
                    while (it.indexOf(
                            query,
                            start,
                            ignoreCase = true
                        ) != -1 && query.isNotBlank()
                    ) {
                        val firstIndex = it.indexOf(query, start, true)
                        val end = firstIndex + query.length
                        append(it.substring(start, firstIndex))
                        withStyle(style = spanStyle) {
                            append(it.substring(firstIndex, end))
                        }
                        start = end
                    }
                    append(it.substring(start, it.length))
                    toAnnotatedString()
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            text = annotatedString,
            fontSize = scale.sp,
            lineHeight = scale.sp * 1.2
        )
    }
}
