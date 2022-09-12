package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.dao.CategoryDao
import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.repository.CategoryRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            val result = categoryDao.getCategories()
            if (result.isSuccessful && result.body() != null) {
                emit(Resource.Success(result.body()?.data ?: emptyList()))
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }

    override fun getBanners(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading)
        try {
            val result = categoryDao.getBanners()
            if (result.isSuccessful && result.body() != null) {
                emit(Resource.Success(result.body()?.data ?: emptyList()))
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }
}