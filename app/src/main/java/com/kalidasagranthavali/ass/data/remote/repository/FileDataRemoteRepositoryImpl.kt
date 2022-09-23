package com.kalidasagranthavali.ass.data.remote.repository

import android.app.Application
import com.kalidasagranthavali.ass.data.remote.apis.FileDataApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.remote.FileDataRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.util.print
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileDataRemoteRepositoryImpl(
    private val fileDataApi: FileDataApi,
    private val application: Application
) : FileDataRemoteRepository {

    override suspend fun getFileData(homeFiles: HomeFiles): Resource<File> {
        return try {
            val result = fileDataApi.getFilesData(homeFiles.file_url)
            result.print("FILE")
            val target = withContext(Dispatchers.IO) {
                File.createTempFile(
                    "${homeFiles.name}_${System.currentTimeMillis()}",
                    ".xml",
                    application.cacheDir
                )
            }
            result.body()?.byteStream()?.use {
                FileOutputStream(target).use { outputStream ->
                    it.copyTo(outputStream)
                }
                Resource.Success(target)
            } ?: Resource.Failure(
                StringUtil.DynamicText("Failed to download file")
            )
        } catch (e: Exception) {
            e.print("FILE")
            if (e is IOException)
                Resource.Failure(
                    StringUtil.DynamicText("Please check your internet connection")
                )
            else Resource.Failure(
                StringUtil.DynamicText(
                    e.localizedMessage ?: "Some server error occurred"
                )
            )
        }
    }
}