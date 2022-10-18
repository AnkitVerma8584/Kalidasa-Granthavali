package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.R
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.CategoryList
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar

@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.bannerState.collectAsState()
    val categories by viewModel.categoryState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = viewModel.categoryQuery.collectAsState().value,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            }
        )
        if (categories.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        CategoryList(
            category = categories.data,
            banner = banners,
            onClick = onClick
        )
        categories.error?.let {
            Text(text = it.asString())
        }
    }
}