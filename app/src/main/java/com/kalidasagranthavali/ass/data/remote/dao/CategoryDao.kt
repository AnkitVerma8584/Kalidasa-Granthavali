package com.kalidasagranthavali.ass.data.remote.dao

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface CategoryDao {


    @GET(Api.GET_BANNERS)
    suspend fun getBanners(): Response<ResultList<String>>

    @GET(Api.GET_CATEGORIES)
    suspend fun getCategories(): Response<ResultList<Category>>
}