package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.Resource

interface FilesRemoteRepository {

    suspend fun getFiles(catId: Int, subCategoryId: Int): Resource<List<HomeFiles>>

}