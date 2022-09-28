package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar

@Composable
fun FileDetailsPage(
    viewModel: FileDetailsViewModel = hiltViewModel()
) {
    val text by viewModel.getData().collectAsState(initial = null)
    val state by viewModel.fileState.collectAsState()
    val query by viewModel.fileDataQuery.collectAsState()
    val scrollState = rememberScrollState()

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
                }, contentAlignment = Alignment.Center
        ) {
            if (state.isLoading)
                CircularProgressIndicator()

            state.error?.let {
                Text(text = it.asString(), color = MaterialTheme.colorScheme.error)
            } ?: Details(text = text, scrollState = scrollState, scale = scale)
        }
    }
}

@Composable
private fun Details(
    text: AnnotatedString?,
    scrollState: ScrollState,
    scale: Float
) {
    if (text == null || text.text.isBlank())
        CircularProgressIndicator()
    else
        Text(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            text = text,
            fontSize = scale.sp,
            lineHeight = scale.sp * 1.2
        )
}
