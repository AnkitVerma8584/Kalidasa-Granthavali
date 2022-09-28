package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.domain.modals.HomeFiles

@Composable
fun FilesList(
    data: List<HomeFiles>,
    onFileClicked: (HomeFiles) -> Unit
) {
    val list = listOf(
        MaterialTheme.colorScheme.background.copy(0.3f),
        MaterialTheme.colorScheme.background.copy(0.5f),
        MaterialTheme.colorScheme.background.copy(0.7f),
        MaterialTheme.colorScheme.background.copy(0.9f)
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Files",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        if (data.isEmpty())
            Text(
                text = "No files found!",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                content = {
                    items(data, key = { it.id }) {
                        FileCard(item = it, textBackground = list, onFileClicked = onFileClicked)
                    }
                })
        }
    }
}