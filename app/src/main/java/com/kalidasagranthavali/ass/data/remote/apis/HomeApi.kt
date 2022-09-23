package com.kalidasagranthavali.ass.data.remote.apis

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Api.GET_BANNER)
    suspend fun getBannerData(): Response<ResultList<String>>

    @GET(Api.GET_CATEGORY)
    suspend fun getCategoryData(): Response<ResultList<HomeCategory>>
}