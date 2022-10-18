package com.kalidasagranthavali.ass.data.remote.mapper

import com.kalidasagranthavali.ass.data.Constants
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDocumentText
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object FileMapper {

    suspend fun HomeFiles.getFileToFilesData(file: File): FilesData? =
        withContext(Dispatchers.IO) {
            try {
                val br = BufferedReader(FileReader(file))
                var line: String?
                val text = mutableListOf<FileDocumentText>()
                val paragraph = StringBuilder()
                var i = 0
                while (br.readLine().also { line = it } != null) {
                    paragraph.append(line)
                    if (i == Constants.PARAGRAPH_LINE) {
                        val index = text.size
                        text.add(FileDocumentText(index, paragraph.toString()))
                        paragraph.clear()
                        i = 0
                    } else paragraph.append("\n")
                    i++
                }
                if (paragraph.isNotBlank()) {
                    val index = text.size
                    text.add(FileDocumentText(index, paragraph.toString()))
                }
                br.close()
                FilesData(this@getFileToFilesData, text.toList())
            } catch (e: Exception) {
                null
            }
        }
}