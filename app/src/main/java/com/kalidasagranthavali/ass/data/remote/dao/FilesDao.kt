package com.kalidasagranthavali.ass.data.remote.dao

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.domain.modals.Files
import com.kalidasagranthavali.ass.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilesDao {

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("sub_category_id") subCategoryId: Int
    ): Response<ResultList<Files>>
}