package com.kalidasagranthavali.ass.data.local.mapper

import com.kalidasagranthavali.ass.data.local.modals.*
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.modals.HomeSubToSubCategory


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

fun List<SubCategory>.mapToHomeSubCategoryList(): List<HomeSubCategory> = this.map { cat ->
    HomeSubCategory(cat.id, cat.cat_id, cat.name, cat.description, cat.image)
}

fun List<HomeSubToSubCategory>.mapToSubToSubCategoryList(): List<SubToSubCategory> = this.map {
    SubToSubCategory(it.id, it.cat_id, it.sub_cat_id, it.name, it.description)
}

fun List<SubToSubCategory>.mapToHomeSubToSubCategoryList(): List<HomeSubToSubCategory> =
    this.map {
        HomeSubToSubCategory(it.id, it.cat_id, it.sub_cat_id, it.name, it.description)
    }


fun List<HomeFiles>.mapToFilesList(): List<Files> = this.map {
    Files(
        it.id,
        it.cat_id,
        it.sub_cat_id,
        it.sub_to_sub_cat_id,
        it.name,
        it.description,
        it.file_url
    )
}

fun List<Files>.mapToHomeFilesList(): List<HomeFiles> = this.map {
    HomeFiles(
        it.id,
        it.cat_id,
        it.sub_cat_id,
        it.sub_to_sub_cat_id,
        it.name,
        it.description,
        it.fileUrl
    )
}

fun Files?.mapToHomeFiles(): HomeFiles? =
    this?.let {
        HomeFiles(id, cat_id, sub_cat_id, sub_to_sub_cat_id, name, description, fileUrl)
    }
