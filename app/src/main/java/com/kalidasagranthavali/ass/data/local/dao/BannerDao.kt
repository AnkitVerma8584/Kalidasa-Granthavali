package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kalidasagranthavali.ass.data.local.modals.Banner
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BannerDao {

    @Insert
    protected abstract suspend fun insert(banners: List<Banner>)

    @Query("DELETE FROM banner_images;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM banner_images;")
    abstract fun getBanners(): Flow<List<Banner>>

    @Transaction
    open suspend fun insertBanners(banners: List<Banner>) {
        delete()
        insert(banners)
    }
}