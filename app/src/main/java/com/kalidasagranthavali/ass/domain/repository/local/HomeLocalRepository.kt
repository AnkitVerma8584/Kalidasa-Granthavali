package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import kotlinx.coroutines.flow.Flow

interface HomeLocalRepository {

    fun getBanners(): Flow<List<String>>

    fun getCategories(query:String): Flow<List<HomeCategory>>

    suspend fun submitBanners(bannerList: List<String>)

    suspend fun submitCategories(categoryList: List<HomeCategory>)
}