package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.HomeApi
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.HomeRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class HomeRemoteRepositoryImpl(
    private val homeApi: HomeApi,
    private val homeLocalRepository: HomeLocalRepository
) : HomeRemoteRepository {

    override fun getBanners(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading)
        try {
            if (homeLocalRepository.hasCachedBanners())
                emit(Resource.Cached(homeLocalRepository.getBanners()))
            val result = homeApi.getBannerData()
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    homeLocalRepository.submitBanners(data)
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Failure(StringUtil.DynamicText(result.body()!!.message)))
                }
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }

    override fun getCategories(): Flow<Resource<List<HomeCategory>>> = flow {
        emit(Resource.Loading)
        try {
            if (homeLocalRepository.hasCachedCategories())
                emit(Resource.Cached(homeLocalRepository.getCategories()))
            val result = homeApi.getCategoryData()
            result.body().print("CATEGORY")
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    homeLocalRepository.submitCategories(data)
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Failure(StringUtil.DynamicText(result.body()!!.message)))
                }
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }
}