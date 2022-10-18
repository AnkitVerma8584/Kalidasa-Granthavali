package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDocumentText

data class FilesData(
    val homeFiles: HomeFiles,
    val file_data: List<FileDocumentText>,
)