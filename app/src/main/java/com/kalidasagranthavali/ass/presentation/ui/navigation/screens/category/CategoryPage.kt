package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.CategoryList
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar

@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.banners.collectAsState(initial = emptyList())
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any category",
            query = viewModel.categoryQuery.collectAsState().value,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            }
        )
        CategoryList(
            data = categories,
            banner = banners,
            onClick = onClick
        )
    }
}