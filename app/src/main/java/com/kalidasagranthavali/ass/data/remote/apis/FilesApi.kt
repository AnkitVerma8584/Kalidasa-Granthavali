package com.kalidasagranthavali.ass.data.remote.apis

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilesApi {

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int
    ): Response<ResultList<HomeFiles>>
}