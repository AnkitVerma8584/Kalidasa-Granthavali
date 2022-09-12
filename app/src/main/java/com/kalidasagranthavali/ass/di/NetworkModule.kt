package com.kalidasagranthavali.ass.di

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.data.remote.dao.CategoryDao
import com.kalidasagranthavali.ass.data.remote.dao.FilesDao
import com.kalidasagranthavali.ass.data.remote.dao.SubCategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @KalidasaRetrofitBuild
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCategoryDao(@KalidasaRetrofitBuild retrofit: Retrofit): CategoryDao =
        retrofit.create(CategoryDao::class.java)

    @Provides
    @Singleton
    fun provideSubCategoryDao(@KalidasaRetrofitBuild retrofit: Retrofit): SubCategoryDao =
        retrofit.create(SubCategoryDao::class.java)

    @Provides
    @Singleton
    fun provideFilesDao(@KalidasaRetrofitBuild retrofit: Retrofit): FilesDao =
        retrofit.create(FilesDao::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class KalidasaRetrofitBuild
