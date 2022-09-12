package com.kalidasagranthavali.ass.domain.repository

import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<Resource<List<Category>>>

    fun getBanners(): Flow<Resource<List<String>>>

}