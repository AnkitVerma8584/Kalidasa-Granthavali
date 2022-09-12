package com.kalidasagranthavali.ass.di

import com.kalidasagranthavali.ass.data.local.dao.BannerDao
import com.kalidasagranthavali.ass.data.local.repositoryImpl.BannerRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.dao.CategoryDao
import com.kalidasagranthavali.ass.data.remote.dao.FilesDao
import com.kalidasagranthavali.ass.data.remote.dao.SubCategoryDao
import com.kalidasagranthavali.ass.data.remote.repository.CategoryRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.repository.FilesRepositoryImpl
import com.kalidasagranthavali.ass.data.remote.repository.SubCategoryRepositoryImpl
import com.kalidasagranthavali.ass.domain.repository.BannerRepository
import com.kalidasagranthavali.ass.domain.repository.CategoryRepository
import com.kalidasagranthavali.ass.domain.repository.FilesRepository
import com.kalidasagranthavali.ass.domain.repository.SubCategoryRepository
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
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(categoryDao)

    @Provides
    @ViewModelScoped
    fun provideSubCategoryRepository(subCategoryDao: SubCategoryDao): SubCategoryRepository =
        SubCategoryRepositoryImpl(subCategoryDao)

    @Provides
    @ViewModelScoped
    fun provideFilesRepository(filesDao: FilesDao): FilesRepository =
        FilesRepositoryImpl(filesDao)

    @Provides
    @ViewModelScoped
    fun provideBannerRepository(bannerDao: BannerDao): BannerRepository =
        BannerRepositoryImpl(bannerDao)
}