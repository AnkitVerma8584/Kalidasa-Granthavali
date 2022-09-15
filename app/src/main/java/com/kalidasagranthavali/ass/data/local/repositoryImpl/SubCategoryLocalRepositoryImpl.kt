package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.SubCategoryDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeSubCategoryList
import com.kalidasagranthavali.ass.data.local.mapper.mapToSubCategoryList
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.repository.local.SubCategoryLocalRepository
import kotlinx.coroutines.flow.Flow

class SubCategoryLocalRepositoryImpl(private val subCategoryDao: SubCategoryDao) :
    SubCategoryLocalRepository {
    override fun getSubCategories(query: String, id: Int): Flow<List<HomeSubCategory>> =
        subCategoryDao.getSubCategories("%$query%", id).mapToHomeSubCategoryList()

    override suspend fun submitSubCategories(subCategory: List<HomeSubCategory>) {
        subCategoryDao.insertCategory(subCategory.mapToSubCategoryList())
    }
}