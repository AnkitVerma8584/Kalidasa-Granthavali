package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FilesRemoteRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<Resource<List<HomeFiles>>>

}