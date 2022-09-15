package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.modals.HomeCategory
import com.kalidasagranthavali.ass.domain.repository.local.HomeLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.HomeRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val homeRemoteRepository: HomeRemoteRepository,
    private val homeLocalRepository: HomeLocalRepository
) : ViewModel() {

    private val _categoryQuery = MutableStateFlow("")
    val categoryQuery get() = _categoryQuery.asStateFlow()

    val banners = homeLocalRepository.getBanners()

    @OptIn(ExperimentalCoroutinesApi::class)
    val categories: Flow<List<HomeCategory>> = categoryQuery.flatMapLatest {
        homeLocalRepository.getCategories(it)
    }

    init {
        viewModelScope.launch(Default) {
            homeRemoteRepository.getHomeDto().collectLatest {
                when (it) {
                    is Resource.Failure -> {

                    }
                    Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        homeLocalRepository.submitBanners(it.result.banner)
                        homeLocalRepository.submitCategories(it.result.category)
                    }
                }
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _categoryQuery.value = newQuery
    }
}