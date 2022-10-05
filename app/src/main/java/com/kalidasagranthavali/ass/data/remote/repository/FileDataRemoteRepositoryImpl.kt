package com.kalidasagranthavali.ass.data.remote.repository

import android.app.Application
import com.kalidasagranthavali.ass.data.remote.Api.getDocumentExtension
import com.kalidasagranthavali.ass.data.remote.apis.FileDataApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.remote.FileDataRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.util.isInValidFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileDataRemoteRepositoryImpl(
    private val fileDataApi: FileDataApi, private val application: Application
) : FileDataRemoteRepository {
    override fun getFileData(homeFiles: HomeFiles): Flow<Resource<File>> = flow {
        try {
            if (homeFiles.file_url.isInValidFile())
                emit(Resource.Failure(StringUtil.DynamicText("Invalid file type")))

            val result = fileDataApi.getFilesData(homeFiles.file_url.getDocumentExtension())
            val target = withContext(Dispatchers.IO) {
                File.createTempFile(
                    homeFiles.name, ".xml", application.cacheDir
                )
            }
            emit(result.body()?.byteStream()?.use { inputStream ->
                FileOutputStream(target).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                Resource.Success(target)
            } ?: Resource.Failure(
                StringUtil.DynamicText("Failed to download file")
            ))
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }
}