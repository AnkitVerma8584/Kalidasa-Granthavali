package com.kalidasagranthavali.ass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.dao.CategoryDao
import com.kalidasagranthavali.ass.data.local.dao.FilesDao
import com.kalidasagranthavali.ass.data.local.dao.SubCategoryDao
import com.kalidasagranthavali.ass.data.local.modals.Banner
import com.kalidasagranthavali.ass.data.local.modals.Category
import com.kalidasagranthavali.ass.data.local.modals.Files
import com.kalidasagranthavali.ass.data.local.modals.SubCategory

@Database(
    entities = [Banner::class, Category::class, SubCategory::class, Files::class],
    exportSchema = false,
    version = 2
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getFilesDao(): FilesDao
}