package com.kalidasagranthavali.ass.data.remote.dao

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.SubCategory
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubCategoryDao {

    @GET(Api.GET_SUBCATEGORIES)
    suspend fun getSubCategories(
        @Query("category_id") categoryId: Int
    ): Response<ResultList<SubCategory>>
}