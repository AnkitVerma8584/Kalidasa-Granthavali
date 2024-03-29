package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.SubCategoryApi
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.repository.local.SubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.SubCategoryRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SubCategoryRemoteRepositoryImpl(
    private val subCategoryApi: SubCategoryApi,
    private val subCategoryLocalRepository: SubCategoryLocalRepository
) :
    SubCategoryRemoteRepository {

    override fun getSubCategories(categoryId: Int): Flow<Resource<List<HomeSubCategory>>> = flow {
        emit(Resource.Loading)
        if (subCategoryLocalRepository.getSubCategoryCount(categoryId) > 0)
            emit(Resource.Cached(subCategoryLocalRepository.getSubCategories(categoryId)))
        emit(
            try {
                val result = subCategoryApi.getSubCategories(categoryId)
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        subCategoryLocalRepository.submitSubCategories(data)
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