package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.dao.FilesDao
import com.kalidasagranthavali.ass.domain.modals.Files
import com.kalidasagranthavali.ass.domain.repository.FilesRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import retrofit2.HttpException
import java.io.IOException

class FilesRepositoryImpl(private val filesDao: FilesDao) : FilesRepository {

    override suspend fun getFiles(subCategoryId: Int): Resource<List<Files>> {
        return try {
            val result = filesDao.getFiles(subCategoryId)
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