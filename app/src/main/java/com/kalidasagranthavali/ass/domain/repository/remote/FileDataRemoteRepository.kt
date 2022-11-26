package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileDataRemoteRepository {

    fun getFileData(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<Resource<File>>
}