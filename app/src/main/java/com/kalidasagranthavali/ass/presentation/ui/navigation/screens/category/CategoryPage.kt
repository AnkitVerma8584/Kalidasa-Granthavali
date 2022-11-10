package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kalidasagranthavali.ass.R
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.CategoryList
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.common.SearchBar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.bannerState.collectAsStateWithLifecycle()
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = viewModel.categoryQuery.collectAsStateWithLifecycle().value,
            onSearchQueryChanged = viewModel::queryChanged
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