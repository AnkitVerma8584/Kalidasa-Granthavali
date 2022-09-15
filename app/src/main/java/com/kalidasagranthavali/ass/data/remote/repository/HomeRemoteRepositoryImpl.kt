package com.kalidasagranthavali.ass.data.remote.repository

import com.kalidasagranthavali.ass.data.remote.apis.HomeApi
import com.kalidasagranthavali.ass.data.remote.dto.HomeDto
import com.kalidasagranthavali.ass.domain.repository.remote.HomeRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class HomeRemoteRepositoryImpl(private val homeApi: HomeApi) : HomeRemoteRepository {

    override fun getHomeDto(): Flow<Resource<HomeDto>> = flow {
        emit(Resource.Loading)
        try {
            val result = homeApi.getHomeData()
            result.body().print()
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    emit(Resource.Success(result.body()?.data!!))
                } else {
                    emit(Resource.Failure(StringUtil.DynamicText(result.body()!!.message)))
                }
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            e.print()
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