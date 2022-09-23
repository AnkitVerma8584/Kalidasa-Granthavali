package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeCategory

interface HomeLocalRepository {

    suspend fun hasCachedBanners(): Boolean

    suspend fun getBanners(): List<String>

    suspend fun hasCachedCategories(): Boolean

    suspend fun getCategories(): List<HomeCategory>

    suspend fun submitBanners(bannerList: List<String>)

    suspend fun submitCategories(categoryList: List<HomeCategory>)
}