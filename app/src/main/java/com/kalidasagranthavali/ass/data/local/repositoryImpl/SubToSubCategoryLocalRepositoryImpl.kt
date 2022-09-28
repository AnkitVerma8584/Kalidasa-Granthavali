package com.kalidasagranthavali.ass.data.local.repositoryImpl

import com.kalidasagranthavali.ass.data.local.dao.SubToSubCategoryDao
import com.kalidasagranthavali.ass.data.local.mapper.mapToHomeSubToSubCategoryList
import com.kalidasagranthavali.ass.data.local.mapper.mapToSubToSubCategoryList
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory
import com.kalidasagranthavali.ass.domain.repository.local.SubToSubCategoryLocalRepository

class SubToSubCategoryLocalRepositoryImpl(private val subToSubCategoryDao: SubToSubCategoryDao) :
    SubToSubCategoryLocalRepository {


    override suspend fun getSubToSubCategories(
        catId: Int,
        subCatId: Int
    ): List<HomeSubToSubCategory> =
        subToSubCategoryDao.getSubToSubCategories(catId, subCatId).mapToHomeSubToSubCategoryList()

    override suspend fun getSubToSubCategoryCount(catId: Int, subCatId: Int): Int =
        subToSubCategoryDao.getSubToSubCategoryCount(catId, subCatId)

    override suspend fun submitSubToSubCategories(subToSubCategory: List<HomeSubToSubCategory>) {
        subToSubCategoryDao.insertSubToSubCategory(subToSubCategory.mapToSubToSubCategoryList())
    }
}