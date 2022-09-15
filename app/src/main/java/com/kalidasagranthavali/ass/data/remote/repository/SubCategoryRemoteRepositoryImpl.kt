package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.SubCategoryApi
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.repository.remote.SubCategoryRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.util.print
import retrofit2.HttpException
import java.io.IOException

class SubCategoryRemoteRepositoryImpl(private val subCategoryApi: SubCategoryApi) :
    SubCategoryRemoteRepository {

    override suspend fun getSubCategories(categoryId: Int): Resource<List<HomeSubCategory>> {
        return try {
            val result = subCategoryApi.getSubCategories(categoryId)
            result.body().print()
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success)
                    Resource.Success(result.body()?.data ?: emptyList())
                else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
            } else {
                Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
            }
        } catch (e: IOException) {
            e.print()
            Resource.Failure(
                StringUtil.DynamicText("Please check your internet connection")
            )
        } catch (e: HttpException) {
            e.print()
            Resource.Failure(
                StringUtil.DynamicText(
                    e.localizedMessage ?: "Some server error occurred"
                )
            )
        }
    }
}