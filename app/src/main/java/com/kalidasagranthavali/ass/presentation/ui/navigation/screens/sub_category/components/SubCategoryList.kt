package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category.components

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
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubCategoryList(
    data: List<HomeSubCategory> = emptyList(),
    onClick: (HomeSubCategory) -> Unit
) {
    LazyColumn {
        stickyHeader {
            Text(
                text = "Sub-Categories",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (data.isEmpty())
            item {
                Text(
                    text = "No results found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        else
            items(items = data, key = { it.id }) { sub_category ->
                SubCategoryCard(data = sub_category, onClick = onClick)
            }
    }
}
