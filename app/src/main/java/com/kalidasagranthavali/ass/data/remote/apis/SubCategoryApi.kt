package com.kalidasagranthavali.ass.data.remote.apis

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubCategoryApi {

    @GET(Api.GET_SUBCATEGORIES)
    suspend fun getSubCategories(
        @Query("cat_id") categoryId: Int
    ): Response<ResultList<HomeSubCategory>>
}