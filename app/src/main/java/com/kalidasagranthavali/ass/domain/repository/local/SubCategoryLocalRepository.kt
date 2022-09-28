package com.kalidasagranthavali.ass.domain.repository.local

import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory

interface SubCategoryLocalRepository {

    suspend fun getSubCategories(id: Int): List<HomeSubCategory>

    suspend fun getSubCategoryCount(id: Int): Int

    suspend fun submitSubCategories(subCategory: List<HomeSubCategory>)

}