package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRemoteRepository {

    fun getBanners(): Flow<Resource<List<String>>>

    fun getCategories(): Flow<Resource<List<HomeCategory>>>

}