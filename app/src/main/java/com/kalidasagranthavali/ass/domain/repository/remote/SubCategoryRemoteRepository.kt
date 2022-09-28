package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SubCategoryRemoteRepository {
    fun getSubCategories(categoryId: Int): Flow<Resource<List<HomeSubCategory>>>
}