package com.kalidasagranthavali.ass.domain.repository

import com.kalidasagranthavali.ass.domain.modals.SubCategory
import com.kalidasagranthavali.ass.domain.utils.Resource

interface SubCategoryRepository {

    suspend fun getSubCategories(categoryId: Int): Resource<List<SubCategory>>
}