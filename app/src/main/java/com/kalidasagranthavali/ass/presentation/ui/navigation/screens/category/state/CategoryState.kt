package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state

import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class CategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeCategory>? = null,
    val error: StringUtil? = null
)
