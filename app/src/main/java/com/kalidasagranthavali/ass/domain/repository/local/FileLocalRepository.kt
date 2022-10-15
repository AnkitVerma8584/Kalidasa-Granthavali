package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeFiles

interface FileLocalRepository {

    suspend fun getFiles(cat_id: Int, sub_cat_id: Int): List<HomeFiles>

    suspend fun getFiles(cat_id: Int, sub_cat_id: Int, sub_to_sub_cat_id: Int): List<HomeFiles>

    suspend fun getFilesCount(cat_id: Int, sub_cat_id: Int): Int

    suspend fun getFilesCount(cat_id: Int, sub_cat_id: Int, sub_to_sub_cat_id: Int): Int

    suspend fun getFileById(fileId: Int): HomeFiles?

    suspend fun submitFiles(files: List<HomeFiles>)
}