package com.kalidasagranthavali.ass.data.remote.fake_repo

import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.repository.CategoryRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeCategoryImpl : CategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            delay(2000)
            val list = (1..50).map {
                Category(
                    it,
                    "Item number $it",
                    "https://www.indiaonlinepages.com/religions/gifs/religions-india.jpg"
                )
            }
            emit(Resource.Success(list))
        } catch (e: IOException) {
            emit(
                Resource.Failure(
                    StringUtil.DynamicText("Please check your internet connection")
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Failure(
                    StringUtil.DynamicText(
                        e.localizedMessage ?: "Some server error occurred"
                    )
                )
            )
        }
    }

    override fun getBanners(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading)
        try {
            delay(2000)
            val banner = listOf(
                "https://images.unsplash.com/photo-1580757468214-c73f7062a5cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8MTYlM0E5fGVufDB8fDB8fA%3D%3D&w=1000&q=80",
                "https://3.bp.blogspot.com/-5vQTc7UBxRA/XFUltHcv0eI/AAAAAAAABz0/-ynSXYBMgD4saeZcKYr-XaLfqKMVMrv0QCKgBGAs/w0/beach-boat-sunrise-scenery-seascape-17-4K.jpg",
                "https://wallpaperaccess.com/full/1879118.jpg",
                "https://g4.img-dpreview.com/5ADC4BE97EF846F2B04F94C0E90F0F0E.jpg"
            )
            emit(Resource.Success(banner))
        } catch (e: IOException) {
            emit(
                Resource.Failure(
                    StringUtil.DynamicText("Please check your internet connection")
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Failure(
                    StringUtil.DynamicText(
                        e.localizedMessage ?: "Some server error occurred"
                    )
                )
            )
        }
    }
}