package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.Banner
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BannerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(banners: List<Banner>)

    @Query("SELECT COUNT(*) FROM banner;")
    abstract fun getBannerCount(): Flow<Int>

    @Query("DELETE FROM banner;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM banner;")
    abstract fun getBanners(): Flow<List<Banner>>

    @Transaction
    open suspend fun insertBanners(banners: List<Banner>) {
        delete()
        insert(banners)
    }
}