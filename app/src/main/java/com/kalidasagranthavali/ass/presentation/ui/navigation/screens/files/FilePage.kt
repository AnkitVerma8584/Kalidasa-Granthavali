package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components.FilesList

@Composable
fun FilePage(
    viewModel: FilesViewModel = hiltViewModel(),
    onFileClicked: (HomeFiles) -> Unit
) {
    val files by viewModel.files.collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any files",
            query = viewModel.query.collectAsState().value,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            }
        )
        FilesList(data = files, onFileClicked = onFileClicked)
    }
}
