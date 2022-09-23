package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import kotlinx.coroutines.flow.Flow

interface FileLocalRepository {

    fun getFiles(query: String, cat_id: Int, sub_cat_id: Int): Flow<List<HomeFiles>>

    suspend fun getFileById(fileId: Int): HomeFiles

    suspend fun submitFiles(files: List<HomeFiles>)
}