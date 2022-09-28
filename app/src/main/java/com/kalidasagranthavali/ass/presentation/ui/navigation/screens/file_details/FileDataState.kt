package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class FileDataState(
    val isLoading: Boolean = false,
    val error: StringUtil? = null
)
