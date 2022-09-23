package com.kalidasagranthavali.ass.data.local.mapper

import com.kalidasagranthavali.ass.data.local.modals.Banner
import com.kalidasagranthavali.ass.data.local.modals.Category
import com.kalidasagranthavali.ass.data.local.modals.Files
import com.kalidasagranthavali.ass.data.local.modals.SubCategory
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun List<String>.mapToBannerList(): List<Banner> = this.map {
    Banner(it)
}


fun List<Banner>.mapToStringList(): List<String> = this.map { it.image }

fun List<HomeCategory>.mapToHomeList(): List<Category> = this.map {
    Category(it.id, it.name, it.image)
}

fun List<Category>.mapToHomeCategoryList(): List<HomeCategory> = this.map { cat ->
    HomeCategory(cat.id, cat.name, cat.image)
}

fun List<HomeSubCategory>.mapToSubCategoryList(): List<SubCategory> = this.map {
    SubCategory(it.id, it.cat_id, it.name, it.description, it.image)
}

fun Flow<List<SubCategory>>.mapToHomeSubCategoryList(): Flow<List<HomeSubCategory>> = this.map {
    it.map { cat ->
        HomeSubCategory(cat.id, cat.cat_id, cat.name, cat.description, cat.image)
    }
}


fun List<HomeFiles>.mapToFilesList(): List<Files> = this.map {
    Files(it.id, it.cat_id, it.sub_cat_id, it.name, it.image, it.file_url)
}

fun Flow<List<Files>>.mapToHomeFilesList(): Flow<List<HomeFiles>> = this.map { cat ->
    cat.map {
        HomeFiles(it.id, it.cat_id, it.sub_cat_id, it.name, it.image, it.fileUrl)
    }
}

fun Files.mapToHomeFiles():HomeFiles = HomeFiles(id, cat_id, sub_cat_id, name, image, fileUrl)
