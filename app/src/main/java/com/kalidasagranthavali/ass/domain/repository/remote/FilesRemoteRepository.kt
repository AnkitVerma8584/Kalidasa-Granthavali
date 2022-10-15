package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.flow.Flow

interface FilesRemoteRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<Resource<List<HomeFiles>>>

    fun getFilesData(
       homeFiles: List<HomeFiles>,
    ):Flow<Resource<List<FilesData>>>
}