package com.kalidasagranthavali.ass.domain.repository

import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    fun getBanners(): Flow<List<String>>

    suspend fun submitBanners(bannerList: List<String>)

}