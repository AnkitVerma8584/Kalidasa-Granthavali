package com.kalidasagranthavali.ass.di

import android.app.Application
import androidx.room.Room
import com.kalidasagranthavali.ass.data.local.RoomDB
import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.dao.CategoryDao
import com.kalidasagranthavali.ass.data.local.dao.FilesDao
import com.kalidasagranthavali.ass.data.local.dao.SubCategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application
    ): RoomDB =
        Room.databaseBuilder(
            application,
            RoomDB::class.java,
            "user_room_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBannerDao(db: RoomDB): BannerDao = db.getBannerDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: RoomDB): CategoryDao = db.getCategoryDao()


    @Singleton
    @Provides
    fun provideSubCategoryDao(db: RoomDB): SubCategoryDao = db.getSubCategoryDao()


    @Singleton
    @Provides
    fun provideFilesDao(db: RoomDB): FilesDao = db.getFilesDao()


}