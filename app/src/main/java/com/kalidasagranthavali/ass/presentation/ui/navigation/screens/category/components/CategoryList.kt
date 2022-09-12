package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.domain.modals.Category


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(
    data: List<Category> = emptyList(),
    banner: List<String>,
    onClick: (Int) -> Unit
) {
    LazyColumn {
        if (banner.isNotEmpty())
            item {
                Slider(banner = banner)
            }
        stickyHeader {
            Text(
                text = "Categories",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(items = data, key = { it.id }) { category ->
            CategoryItem(data = category, onClick = onClick)
        }
    }
}
