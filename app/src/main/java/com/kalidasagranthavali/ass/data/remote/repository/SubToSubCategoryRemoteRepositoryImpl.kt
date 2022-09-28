package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.SubToSubCategoryApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.SubToSubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SubToSubCategoryRemoteRepositoryImpl(
    private val subToSubCategoryApi: SubToSubCategoryApi,
    private val subToSubCategoryLocalRepository: SubToSubCategoryLocalRepository,
    private val fileLocalRepository: FileLocalRepository
) : SubToSubCategoryRemoteRepository {

    override fun getSubToSubCategories(
        categoryId: Int, subCategoryId: Int
    ): Flow<Resource<List<HomeSubToSubCategory>>> = flow {
        emit(Resource.Loading)
        if (subToSubCategoryLocalRepository.getSubToSubCategoryCount(
                categoryId,
                subCategoryId
            ) > 0
        ) emit(
            Resource.Cached(
                subToSubCategoryLocalRepository.getSubToSubCategories(
                    categoryId, subCategoryId
                )
            )
        )
        emit(
            try {
                val result = subToSubCategoryApi.getSubToSubCategories(categoryId, subCategoryId)
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        subToSubCategoryLocalRepository.submitSubToSubCategories(data)
                        Resource.Success(data)
                    } else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
                } else {
                    Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
                }
            } catch (e: Exception) {
                Resource.Failure(
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }

    override fun getFiles(catId: Int, subCategoryId: Int): Flow<Resource<List<HomeFiles>>> = flow {
        emit(Resource.Loading)
        if (fileLocalRepository.getFilesCount(catId, subCategoryId) > 0) emit(
            Resource.Success(
                fileLocalRepository.getFiles(catId, subCategoryId)
            )
        )
        emit(
            try {
                val result = subToSubCategoryApi.getFiles(catId, subCategoryId)
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
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }
}