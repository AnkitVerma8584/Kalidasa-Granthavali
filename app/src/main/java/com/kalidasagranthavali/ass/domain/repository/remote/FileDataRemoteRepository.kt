package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.utils.Resource
import java.io.File

interface FileDataRemoteRepository {

    suspend fun getFileData(homeFiles: HomeFiles): Resource<File>
}