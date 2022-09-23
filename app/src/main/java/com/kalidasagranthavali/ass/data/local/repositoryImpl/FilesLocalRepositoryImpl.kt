package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.FilesDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToFilesList
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeFiles
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeFilesList
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import kotlinx.coroutines.flow.Flow

class FilesLocalRepositoryImpl(private val filesDao: FilesDao) :
    FileLocalRepository {

    override fun getFiles(query: String, cat_id: Int, sub_cat_id: Int): Flow<List<HomeFiles>> =
        filesDao.getFiles("%$query%", cat_id, sub_cat_id).mapToHomeFilesList()

    override suspend fun getFileById(fileId: Int): HomeFiles =
        filesDao.getFileById(fileId).mapToHomeFiles()


    override suspend fun submitFiles(files: List<HomeFiles>) {
        filesDao.insertFiles(files.mapToFilesList())
    }
}