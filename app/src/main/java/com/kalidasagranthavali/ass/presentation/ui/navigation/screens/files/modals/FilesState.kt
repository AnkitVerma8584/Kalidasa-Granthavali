package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.StringUtil

data class FilesState(
    val isLoading: Boolean = false,
    val data: List<HomeFiles>? = null,
    val error: StringUtil? = null
)
