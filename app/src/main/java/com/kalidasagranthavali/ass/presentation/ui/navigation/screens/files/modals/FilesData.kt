package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals

import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDocumentText

data class FilesData(
    val file_id: Int,
    val file_name: String,
    val file_data: List<FileDocumentText>,
)