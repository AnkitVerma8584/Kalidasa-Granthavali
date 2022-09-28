package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.FilesApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FilesRemoteRepositoryImpl(
    private val filesApi: FilesApi,
    private val fileLocalRepository: FileLocalRepository
) : FilesRemoteRepository {

    override fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<Resource<List<HomeFiles>>> = flow {
        emit(Resource.Loading)
        if (fileLocalRepository.getFilesCount(catId, subCategoryId) > 0)
            emit(Resource.Success(fileLocalRepository.getFiles(catId, subCategoryId)))
        emit(
            try {
                val result = filesApi.getFiles(catId, subCategoryId, subToSubCategoryId)
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        fileLocalRepository.submitFiles(data)
                        Resource.Success(data)
                    } else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
                } else {
                    Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
                }
            } catch (e: Exception) {
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }

}