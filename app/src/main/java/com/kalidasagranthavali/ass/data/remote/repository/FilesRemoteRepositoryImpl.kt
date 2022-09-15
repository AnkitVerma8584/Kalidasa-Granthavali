package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.FilesApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import retrofit2.HttpException
import java.io.IOException

class FilesRemoteRepositoryImpl(private val filesApi: FilesApi) : FilesRemoteRepository {

    override suspend fun getFiles(catId: Int, subCategoryId: Int): Resource<List<HomeFiles>> {
        return try {
            val result = filesApi.getFiles(catId, subCategoryId)
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