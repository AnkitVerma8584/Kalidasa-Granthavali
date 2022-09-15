package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.data.remote.dto.HomeDto
import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRemoteRepository {

    fun getHomeDto(): Flow<Resource<HomeDto>>

}