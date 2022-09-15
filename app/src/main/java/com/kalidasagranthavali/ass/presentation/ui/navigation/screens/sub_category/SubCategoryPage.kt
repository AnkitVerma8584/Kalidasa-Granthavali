package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category.components.SubCategoryList

@Composable
fun SubCategoryPage(
    onNavigateToFile: (HomeSubCategory) -> Unit,
    viewModel: SubCategoryViewModel = hiltViewModel()
) {
    val subCategories by viewModel.subCategories.collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any sub-category",
            query = viewModel.subCategoryQuery.collectAsState().value,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            }
        )
        SubCategoryList(
            data = subCategories,
            onClick = onNavigateToFile
        )
    }

}