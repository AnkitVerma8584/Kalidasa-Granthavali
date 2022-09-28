package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category

import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class SubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubCategory>? = null,
    val error: StringUtil? = null
)
