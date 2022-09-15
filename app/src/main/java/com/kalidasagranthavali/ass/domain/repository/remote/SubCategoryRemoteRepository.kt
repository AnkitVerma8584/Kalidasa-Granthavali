package com.kalidasagranthavali.ass.domain.repository.remote

import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.utils.Resource

interface SubCategoryRemoteRepository {
    suspend fun getSubCategories(categoryId: Int): Resource<List<HomeSubCategory>>
}