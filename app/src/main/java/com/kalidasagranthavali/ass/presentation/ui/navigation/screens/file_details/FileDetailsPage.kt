package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components.DocumentText
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components.ScrollToTopButton
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components.SearchedText
import kotlinx.coroutines.launch

@Composable
fun FileDetailsPage(
    viewModel: FileDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.fileState.collectAsState()
    val query by viewModel.fileDataQuery.collectAsState()
    var scale by remember { mutableStateOf(16f) }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(query = query,
            onSearchQueryChanged = { viewModel.updateQuery(it) },
            onClearPressed = { viewModel.updateQuery() },
            hint = "Search for any text...",
            minimumLetter = 3
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    if ((scale * zoom) in 11.0f..60.0f) scale *= zoom
                }
            }) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error?.let {
                Text(
                    text = it.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } ?: DocumentContent(
                viewModel = viewModel,
                scale = scale,
                query = query,
                scrollIndex = viewModel.getScrollIndex()
            )
        }
    }
}

@Composable
private fun BoxScope.DocumentContent(
    viewModel: FileDetailsViewModel, query: String, scale: Float, scrollIndex: Int
) {
    val text by viewModel.text.collectAsState()
    val searchedText by viewModel.searchedText.collectAsState()


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()



    if (text.isEmpty()) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    else {
        LazyColumn(
            state = listState, modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            if (query.length > 2) {
                item {
                    Text(
                        text = "Found ${searchedText.size} results.",
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                if (searchedText.isNotEmpty()) {
                    items(searchedText) { content ->
                        SearchedText(query = query, content = content, onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(searchedText.size - 1 + it)
                            }
                        })
                    }
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
            items(text) { item ->
                DocumentText(query = query, text = item, scale = scale)
            }
        }
        ScrollToTopButton(listState = listState, coroutineScope = coroutineScope)

        val totalItems by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
        if (scrollIndex in searchedText.size until totalItems) {
            LaunchedEffect(Unit) {
                listState.animateScrollToItem(searchedText.size - 1 + scrollIndex)
                viewModel.removeIndexFlag()
            }
        }

    }
}
