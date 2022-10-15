package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components.SearchBar
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category.components.SubToSubCategoryContent

@Composable
fun SubToSubCategoryPage(
    viewModel: SubToSubCategoryViewModel = hiltViewModel(),
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    onFileClicked: (HomeFiles) -> Unit
) {
    val subToSubCategories by viewModel.subToSubCategoryState.collectAsState()
    val files by viewModel.fileState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any sub categories or  files",
            query = viewModel.query.collectAsState().value,
            onClearPressed = {
                viewModel.queryChanged()
            },
            onSearchQueryChanged = {
                viewModel.queryChanged(it)
            }
        )
        SubToSubCategoryContent(subToSubCategories.data, onSubToSubCategoryClick, files.data, onFileClicked)
    }
}


