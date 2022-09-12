package com.kalidasagranthavali.ass.data.local

import androidx.room.Database

import androidx.room.RoomDatabase
import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.modals.Banner


@Database(entities = [Banner::class], exportSchema = false, version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao
}