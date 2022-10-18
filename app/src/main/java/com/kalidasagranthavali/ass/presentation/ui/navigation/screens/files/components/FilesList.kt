package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components.SearchedText
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(
    searchedContent: List<FilesData>,
    data: List<HomeFiles>,
    query: String,
    onFileClicked: (homeFiles: HomeFiles, query: String, index: Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        searchedContent.forEach { fileData ->
            stickyHeader {
                Text(
                    text = fileData.homeFiles.name, modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(fileData.file_data) { text ->
                SearchedText(query = query, content = text, onClick = {
                    onFileClicked(fileData.homeFiles, query, it)
                })
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
        stickyHeader {
            Text(
                text = "Files",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (data.isEmpty())
            item {
                Text(
                    text = "No files found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        else {
            items(data, key = { it.id }) {
                FileCard(item = it, onFileClicked = onFileClicked)
            }
        }
    }
}