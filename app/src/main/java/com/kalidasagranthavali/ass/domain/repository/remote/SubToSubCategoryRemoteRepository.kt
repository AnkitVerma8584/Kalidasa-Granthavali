package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.flow.Flow

interface SubToSubCategoryRemoteRepository {

    fun getSubToSubCategories(
        categoryId: Int,
        subCategoryId: Int
    ): Flow<Resource<List<HomeSubToSubCategory>>>

    fun getFiles(catId: Int, subCategoryId: Int): Flow<Resource<List<HomeFiles>>>

    fun getFilesData(
        homeFiles: List<HomeFiles>,
    ):Flow<Resource<List<FilesData>>>

}