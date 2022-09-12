package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.CategoryList
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state.CategoryState

@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val state by viewModel.categoryState.collectAsState(CategoryState())
    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any category",
            query = viewModel.categoryQuery.collectAsState(),
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            },
            onSearchedPressed = {
                viewModel.querySubmitted()
            }
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.data?.let {
            CategoryList(
                data = it,
                banner = viewModel.banners.collectAsState(initial = emptyList()).value,
                onClick = onClick
            )
        }
    }
}