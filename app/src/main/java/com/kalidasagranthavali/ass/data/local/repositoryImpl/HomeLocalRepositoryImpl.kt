package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.dao.CategoryDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToBannerList
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeCategoryList
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeList
import com.kalidasagranthavali.ass.data.local.mapper.mapToStringList
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository
import kotlinx.coroutines.flow.Flow

class HomeLocalRepositoryImpl(
    private val bannerDao: BannerDao,
    private val categoryDao: CategoryDao
) :
    HomeLocalRepository {

    override fun getBanners(): Flow<List<String>> = bannerDao.getBanners().mapToStringList()

    override fun getCategories(query: String): Flow<List<HomeCategory>> =
        categoryDao.getCategories("%$query%").mapToHomeCategoryList()

    override suspend fun submitBanners(bannerList: List<String>) {
        bannerDao.insertBanners(bannerList.mapToBannerList())
    }

    override suspend fun submitCategories(categoryList: List<HomeCategory>) {
        categoryDao.insertCategory(categoryList.mapToHomeList())
    }
}