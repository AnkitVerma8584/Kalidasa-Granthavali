package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state

import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class BannerState(
    val isLoading: Boolean = false,
    val data: List<String>? = null,
    val error: StringUtil? = null
)
