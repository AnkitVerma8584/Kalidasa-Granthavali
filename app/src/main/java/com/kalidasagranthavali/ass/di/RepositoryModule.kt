package com.kalidasagranthavali.ass.di

import android.app.Application
import com.kalidasagranthavali.ass.data.local.dao.*
import com.kalidasagranthavali.ass.data.local.repositoryImpl.FilesLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.local.repositoryImpl.HomeLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.local.repositoryImpl.SubCategoryLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.local.repositoryImpl.SubToSubCategoryLocalRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.apis.*
import com.kalidasagranthavali.ass.data.remote.repository.*
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.SubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.local.SubToSubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.*
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
    fun provideSubToSubCategoryLocalRepository(
        subToSubCategoryDao: SubToSubCategoryDao
    ): SubToSubCategoryLocalRepository =
        SubToSubCategoryLocalRepositoryImpl(subToSubCategoryDao)

    @Provides
    @ViewModelScoped
    fun provideFilesLocalRepository(
        filesDao: FilesDao
    ): FileLocalRepository =
        FilesLocalRepositoryImpl(filesDao)

    @Provides
    @ViewModelScoped
    fun provideHomeRemoteRepository(
        homeApi: HomeApi,
        homeLocalRepository: HomeLocalRepository
    ): HomeRemoteRepository =
        HomeRemoteRepositoryImpl(homeApi, homeLocalRepository)

    @Provides
    @ViewModelScoped
    fun provideSubCategoryRepository(
        subCategoryApi: SubCategoryApi,
        subCategoryLocalRepository: SubCategoryLocalRepository
    ): SubCategoryRemoteRepository =
        SubCategoryRemoteRepositoryImpl(subCategoryApi, subCategoryLocalRepository)

    @Provides
    @ViewModelScoped
    fun provideSubToSubCategoryRepository(
        subToSubCategoryApi: SubToSubCategoryApi,
        subToSubCategoryLocalRepository: SubToSubCategoryLocalRepository,
        fileLocalRepository: FileLocalRepository,
        filesDataApi: FileDataApi,
        application: Application
    ): SubToSubCategoryRemoteRepository =
        SubToSubCategoryRemoteRepositoryImpl(
            subToSubCategoryApi,
            subToSubCategoryLocalRepository,
            fileLocalRepository,
            application, filesDataApi
        )

    @Provides
    @ViewModelScoped
    fun provideFileRepository(
        filesApi: FilesApi,
        fileLocalRepository: FileLocalRepository,
        filesDataApi: FileDataApi,
        application: Application
    ): FilesRemoteRepository =
        FilesRemoteRepositoryImpl(filesApi, fileLocalRepository, application, filesDataApi)

    @Provides
    @ViewModelScoped
    fun provideFileDataRepository(
        filesApi: FileDataApi,
        application: Application
    ): FileDataRemoteRepository =
        FileDataRemoteRepositoryImpl(filesApi, application)


}
