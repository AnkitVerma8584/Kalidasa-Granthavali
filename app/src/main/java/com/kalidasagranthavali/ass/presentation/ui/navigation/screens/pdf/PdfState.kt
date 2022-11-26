package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.pdf

import com.kalidasagranthavali.ass.domain.utils.StringUtil
import java.io.File

data class PdfState(
    val isLoading: Boolean = false,
    val error: StringUtil? = null,
    val file: File? = null
)