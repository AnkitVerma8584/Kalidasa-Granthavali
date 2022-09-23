package com.kalidasagranthavali.ass.di

import android.app.Application
import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.dao.CategoryDao
import com.kalidasagranthavali.ass.data.local.dao.FilesDao
import com.kalidasagranthavali.ass.data.local.dao.SubCategoryDao
import com.kalidasagranthavali.ass.data.local.repositoryImpl.FilesLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.local.repositoryImpl.HomeLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.local.repositoryImpl.SubCategoryLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.apis.FileDataApi
import com.kalidasagranthavali.ass.data.remote.apis.FilesApi
import com.kalidasagranthavali.ass.data.remote.apis.HomeApi
import com.kalidasagranthavali.ass.data.remote.apis.SubCategoryApi
import com.kalidasagranthavali.ass.data.remote.repository.FileDataRemoteRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.repository.FilesRemoteRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.repository.HomeRemoteRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.repository.SubCategoryRemoteRepositoryImpl
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.SubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FileDataRemoteRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
import com.kalidasagranthavali.ass.domain.repository.remote.HomeRemoteRepository
import com.kalidasagranthavali.ass.domain.repository.remote.SubCategoryRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRemoteRepository(
        homeApi: HomeApi,
        homeLocalRepository: HomeLocalRepository
    ): HomeRemoteRepository =
        HomeRemoteRepositoryImpl(homeApi, homeLocalRepository)

    @Provides
    @ViewModelScoped
    fun provideSubCategoryRepository(subCategoryApi: SubCategoryApi): SubCategoryRemoteRepository =
        SubCategoryRemoteRepositoryImpl(subCategoryApi)

    @Provides
    @ViewModelScoped
    fun provideFileRepository(filesApi: FilesApi): FilesRemoteRepository =
        FilesRemoteRepositoryImpl(filesApi)

    @Provides
    @ViewModelScoped
    fun provideFileDataRepository(
        filesApi: FileDataApi,
        application: Application
    ): FileDataRemoteRepository =
        FileDataRemoteRepositoryImpl(filesApi, application)

    @Provides
    @ViewModelScoped
    fun provideHomeLocalRepository(
        bannerDao: BannerDao,
        categoryDao: CategoryDao
    ): HomeLocalRepository =
        HomeLocalRepositoryImpl(bannerDao, categoryDao)

    @Provides
    @ViewModelScoped
    fun provideSubCategoryLocalRepository(
        subCategoryDao: SubCategoryDao
    ): SubCategoryLocalRepository =
        SubCategoryLocalRepositoryImpl(subCategoryDao)

    @Provides
    @ViewModelScoped
    fun provideFilesLocalRepository(
        filesDao: FilesDao
    ): FileLocalRepository =
        FilesLocalRepositoryImpl(filesDao)

}
