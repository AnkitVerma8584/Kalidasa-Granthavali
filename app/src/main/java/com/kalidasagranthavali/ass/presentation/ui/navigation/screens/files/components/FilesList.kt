package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.domain.modals.HomeFiles

@Composable
fun FilesList(
    data: List<HomeFiles>,
    onFileClicked: (HomeFiles) -> Unit
) {
    val list = listOf(
        Color.Black.copy(0.2f),
        Color.Black.copy(0.4f),
        Color.Black.copy(0.6f),
        Color.Black.copy(0.8f)
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        content = {
            items(data, key = { it.id }) {
                FileCard(item = it, textBackground = list, onFileClicked)
            }
        })
}