package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.R
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state.BannerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CategoryList(
    category: List<HomeCategory>?,
    banner: BannerState,
    onClick: (HomeCategory) -> Unit
) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        banner.data?.let {
            item {
                Slider(banner = it)
            }
        }
        stickyHeader {
            Text(
                text = stringResource(id = R.string.category),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        category?.let { list ->
            items(items = list, key = { it.id }) { category ->
                CategoryItem(data = category, onClick = onClick)
            }
        }
    }
}
