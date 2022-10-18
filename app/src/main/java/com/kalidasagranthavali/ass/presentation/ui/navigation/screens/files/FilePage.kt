package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components.FilesList

@Composable
fun FilePage(
    viewModel: FilesViewModel = hiltViewModel(),
    onFileClicked: (name: String, id: Int, query: String, index: Int) -> Unit
) {
    val files by viewModel.fileState.collectAsState()
    val searchedData by viewModel.fileData.collectAsState()
    val query by viewModel.query.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any files",
            query = query,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            },
            minimumLetter = 3
        )
        files.data?.let {
            FilesList(
                searchedContent = searchedData,
                data = it,
                query,
                onFileClicked = onFileClicked
            )
        } ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
