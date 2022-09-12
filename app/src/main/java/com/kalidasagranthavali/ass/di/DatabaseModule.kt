package com.kalidasagranthavali.ass.di

import android.app.Application
import androidx.room.Room
import com.kalidasagranthavali.ass.data.local.RoomDB
import com.kalidasagranthavali.ass.data.local.dao.BannerDao
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
    fun provideCartDao(db: RoomDB): BannerDao = db.getBannerDao()


}