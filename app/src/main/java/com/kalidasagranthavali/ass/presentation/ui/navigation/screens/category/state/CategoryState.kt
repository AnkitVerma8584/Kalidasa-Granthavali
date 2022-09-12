package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state

import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class CategoryState(
    val isLoading: Boolean = false,
    val data: List<Category>? = null,
    val error: StringUtil? = null
)
