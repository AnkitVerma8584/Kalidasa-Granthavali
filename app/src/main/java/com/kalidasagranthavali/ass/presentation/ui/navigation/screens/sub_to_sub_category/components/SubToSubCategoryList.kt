package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components.SearchedText
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components.FileCard
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubToSubCategoryContent(
    query: String,
    searchedContent: List<FilesData>,
    subToSubCategory: List<HomeSubToSubCategory>?,
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    files: List<HomeFiles>?,
    onFileClicked: (HomeFiles, String, Int) -> Unit
) {
    LazyColumn {
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

        subToSubCategory?.let { list ->
            stickyHeader {
                Text(
                    text = "Sub-to-sub-category",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (list.isEmpty())
                item {
                    Text(
                        text = "No sub-to-sub-categories found!",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        )
                    )
                }
            else
                items(items = list, key = { it.id }) { sub_category ->
                    SubToSubCategoryCard(data = sub_category, onClick = onSubToSubCategoryClick)
                }
        } ?: item {
            Loading()
        }
        files?.let { list ->
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
            if (list.isEmpty())
                item {
                    Text(
                        text = "No files found!",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        )
                    )
                }
            else
                items(list, key = { it.name }) {
                    FileCard(item = it, onFileClicked = onFileClicked)
                }
        } ?: item {
            Loading()
        }
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxWidth())
    {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}