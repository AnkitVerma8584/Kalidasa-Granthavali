package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.dao.CategoryDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToBannerList
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeCategoryList
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeList
import com.kalidasagranthavali.ass.data.local.mapper.mapToStringList
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository

class HomeLocalRepositoryImpl(
    private val bannerDao: BannerDao,
    private val categoryDao: CategoryDao
) :
    HomeLocalRepository {
    override suspend fun hasCachedBanners(): Boolean = bannerDao.getBannerCount() != 0

    override suspend fun getBanners(): List<String> = bannerDao.getBanners().mapToStringList()

    override suspend fun hasCachedCategories(): Boolean = categoryDao.getCategoryCount() != 0

    override suspend fun getCategories(): List<HomeCategory> =
        categoryDao.getCategories().mapToHomeCategoryList()

    override suspend fun submitBanners(bannerList: List<String>) {
        bannerDao.insertBanners(bannerList.mapToBannerList())
    }

    override suspend fun submitCategories(categoryList: List<HomeCategory>) {
        categoryDao.insertCategory(categoryList.mapToHomeList())
    }
}