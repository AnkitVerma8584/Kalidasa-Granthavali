package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category.components

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
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubToSubCategoryList(
    data: List<HomeSubToSubCategory> = emptyList(),
    onClick: (HomeSubToSubCategory) -> Unit
) {
    LazyColumn {
        stickyHeader {
            Text(
                text = "Sub-To-Sub-Categories",
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
                    text = "No sub-to-sub-categories found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        else
            items(items = data, key = { it.id }) { sub_category ->
                SubToSubCategoryCard(data = sub_category, onClick = onClick)
            }
    }
}
