package com.kalidasagranthavali.ass.data.remote.repository

import android.app.Application
import android.content.Context
import com.kalidasagranthavali.ass.data.remote.Api.getDocumentExtension
import com.kalidasagranthavali.ass.data.remote.apis.FileDataApi
import com.kalidasagranthavali.ass.data.remote.apis.FilesApi
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDocumentText
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesData
import com.kalidasagranthavali.ass.util.print
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class FilesRemoteRepositoryImpl(
    private val filesApi: FilesApi,
    private val fileLocalRepository: FileLocalRepository,
    private val application: Application,
    private val fileDataApi: FileDataApi
) : FilesRemoteRepository {

    override fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<Resource<List<HomeFiles>>> = flow {
        emit(Resource.Loading)
        if (fileLocalRepository.getFilesCount(catId, subCategoryId) > 0)
            emit(Resource.Success(fileLocalRepository.getFiles(catId, subCategoryId)))
        emit(
            try {
                val result = filesApi.getFiles(catId, subCategoryId, subToSubCategoryId)
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        fileLocalRepository.submitFiles(data)
                        Resource.Success(data)
                    } else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
                } else {
                    Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
                }
            } catch (e: Exception) {
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }

    override fun getFilesData(homeFiles: List<HomeFiles>): Flow<Resource<List<FilesData>>> = flow {
        emit(Resource.Loading)
        try {
            val fileDataList = mutableListOf<FilesData>()
            for (homeFile in homeFiles) {
                val file = File(application.filesDir, "${homeFile.name}_${homeFile.id}.txt")
                val downloadedFile = if (!file.exists()) {
                    val result = fileDataApi.getFilesData(homeFile.file_url.getDocumentExtension())
                    result.body()?.byteStream()?.use { inputStream ->
                        application.openFileOutput(file.name, Context.MODE_PRIVATE)
                            .use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        file
                    }
                } else file

                val fileData = downloadedFile?.let { homeFile.getFileToFilesData(it) }
                fileData?.let {
                    fileDataList.add(it)
                }
            }
            if (fileDataList.isEmpty())
                emit(Resource.Failure(StringUtil.DynamicText("No results")))
            else emit(Resource.Success(fileDataList.toList()))
        } catch (e: Exception) {
            e.print()
            emit(Resource.Failure(StringUtil.DynamicText("No results ${e.message}")))
        }
    }

    private suspend fun HomeFiles.getFileToFilesData(file: File): FilesData? =
        withContext(IO) {
            try {
                val br = BufferedReader(FileReader(file))
                var line: String?
                val text = mutableListOf<FileDocumentText>()
                val paragraph = StringBuilder()
                var i = 0
                while (br.readLine().also { line = it } != null) {
                    paragraph.append(line)
                    if (i == 2) {
                        val index = text.size
                        text.add(FileDocumentText(index, paragraph.toString()))
                        paragraph.clear()
                        i = 0
                    } else paragraph.append("\n")
                    i++
                }
                if (paragraph.isNotBlank()) {
                    val index = text.size
                    text.add(FileDocumentText(index, paragraph.toString()))
                }
                br.close()
                FilesData(this@getFileToFilesData.id, this@getFileToFilesData.name, text.toList())
            } catch (e: Exception) {
                null
            }
        }

}