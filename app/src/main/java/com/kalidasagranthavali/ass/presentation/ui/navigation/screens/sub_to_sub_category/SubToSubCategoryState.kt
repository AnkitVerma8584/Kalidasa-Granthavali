package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category

import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class SubToSubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubToSubCategory>? = null,
    val error: StringUtil? = null
)
