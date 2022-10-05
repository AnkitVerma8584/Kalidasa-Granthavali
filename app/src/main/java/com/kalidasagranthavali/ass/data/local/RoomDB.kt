package com.kalidasagranthavali.ass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalidasagranthavali.ass.data.local.dao.*
import com.kalidasagranthavali.ass.data.local.modals.*

@Database(
    entities = [Banner::class, Category::class, SubCategory::class, SubToSubCategory::class, Files::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getSubToSubCategoryDao(): SubToSubCategoryDao
    abstract fun getFilesDao(): FilesDao
}