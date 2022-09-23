package com.kalidasagranthavali.ass.di

import com.kalidasagranthavali.ass.data.remote.Api
import com.kalidasagranthavali.ass.data.remote.apis.FileDataApi
import com.kalidasagranthavali.ass.data.remote.apis.FilesApi
import com.kalidasagranthavali.ass.data.remote.apis.HomeApi
import com.kalidasagranthavali.ass.data.remote.apis.SubCategoryApi
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
    fun provideHomeDao(@KalidasaRetrofitBuild retrofit: Retrofit): HomeApi =
        retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideSubCategoryDao(@KalidasaRetrofitBuild retrofit: Retrofit): SubCategoryApi =
        retrofit.create(SubCategoryApi::class.java)

    @Provides
    @Singleton
    fun provideFilesDao(@KalidasaRetrofitBuild retrofit: Retrofit): FilesApi =
        retrofit.create(FilesApi::class.java)

    @Provides
    @Singleton
    fun provideFileDataApi(): FileDataApi = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .build()
        .create(FileDataApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class KalidasaRetrofitBuild
