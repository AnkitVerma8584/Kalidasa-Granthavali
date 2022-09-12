package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToBannerList
import com.kalidasagranthavali.ass.data.local.mapper.mapToStringList
import com.kalidasagranthavali.ass.domain.repository.BannerRepository
import kotlinx.coroutines.flow.Flow

class BannerRepositoryImpl(private val bannerDao: BannerDao) :
    BannerRepository {

    override fun getBanners(): Flow<List<String>> = bannerDao.getBanners().mapToStringList()

    override suspend fun submitBanners(bannerList: List<String>) {
        bannerDao.insertBanners(bannerList.mapToBannerList())
    }
}