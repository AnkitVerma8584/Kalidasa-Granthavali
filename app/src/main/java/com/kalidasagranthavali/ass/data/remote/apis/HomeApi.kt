package com.kalidasagranthavali.ass.data.remote.apis

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.data.remote.dto.HomeDto
import com.kalidasagranthavali.ass.domain.utils.Result
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Api.GET_HOME)
    suspend fun getHomeData(): Response<Result<HomeDto>>
}