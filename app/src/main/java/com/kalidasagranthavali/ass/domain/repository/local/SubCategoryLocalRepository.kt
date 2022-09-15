package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import kotlinx.coroutines.flow.Flow

interface SubCategoryLocalRepository {

    fun getSubCategories(query: String, id: Int): Flow<List<HomeSubCategory>>

    suspend fun submitSubCategories(subCategory: List<HomeSubCategory>)

}