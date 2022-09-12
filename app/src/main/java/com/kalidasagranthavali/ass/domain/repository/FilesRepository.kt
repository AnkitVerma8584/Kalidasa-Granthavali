package com.kalidasagranthavali.ass.domain.repository

import com.kalidasagranthavali.ass.domain.modals.Files
import com.kalidasagranthavali.ass.domain.utils.Resource

interface FilesRepository {

    suspend fun getFiles(subCategoryId: Int): Resource<List<Files>>

}