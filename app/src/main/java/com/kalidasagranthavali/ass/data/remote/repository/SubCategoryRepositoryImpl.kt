package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.dao.SubCategoryDao
import com.kalidasagranthavali.ass.domain.modals.SubCategory
import com.kalidasagranthavali.ass.domain.repository.SubCategoryRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import retrofit2.HttpException
import java.io.IOException

class SubCategoryRepositoryImpl(private val subCategoryDao: SubCategoryDao) :
    SubCategoryRepository {

    override suspend fun getSubCategories(categoryId: Int): Resource<List<SubCategory>> {
        return try {
            val result = subCategoryDao.getSubCategories(categoryId)
            if (result.isSuccessful && result.body() != null) {
                Resource.Success(result.body()?.data ?: emptyList())
            } else {
                Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
            }
        } catch (e: IOException) {
            Resource.Failure(
                StringUtil.DynamicText("Please check your internet connection")
            )
        } catch (e: HttpException) {
            Resource.Failure(
                StringUtil.DynamicText(
                    e.localizedMessage ?: "Some server error occurred"
                )
            )
        }
    }
}