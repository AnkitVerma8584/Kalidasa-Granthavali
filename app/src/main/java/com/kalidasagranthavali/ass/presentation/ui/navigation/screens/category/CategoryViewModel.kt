package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.data.remote.fake_repo.FakeCategoryImpl
import com.kalidasagranthavali.ass.domain.modals.Category
import com.kalidasagranthavali.ass.domain.repository.BannerRepository
import com.kalidasagranthavali.ass.domain.repository.CategoryRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.state.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {

    private val _categoryQuery = MutableStateFlow("")
    val categoryQuery get() = _categoryQuery.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryState())
    val banners = bannerRepository.getBanners()

    @OptIn(ExperimentalCoroutinesApi::class)
    val categoryState: Flow<CategoryState> = combine(_categoryState, categoryQuery) { data, query ->
        Pair(data, query)
    }.flatMapLatest { (data, query) ->
        flow {
            val newList: List<Category>? = data.data?.filter { it.category.contains(query) }
            emit(data.copy(data = newList))
        }
    }

    init {
        viewModelScope.launch(Default) {
            FakeCategoryImpl().getCategories().collectLatest {
                _categoryState.value = when (it) {
                    is Resource.Failure -> CategoryState(error = it.error)
                    Resource.Loading -> CategoryState(isLoading = true)
                    is Resource.Success -> {
                        bannerRepository.submitBanners(
                            listOf(
                                "https://images.unsplash.com/photo-1580757468214-c73f7062a5cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8MTYlM0E5fGVufDB8fDB8fA%3D%3D&w=1000&q=80",
                                "https://3.bp.blogspot.com/-5vQTc7UBxRA/XFUltHcv0eI/AAAAAAAABz0/-ynSXYBMgD4saeZcKYr-XaLfqKMVMrv0QCKgBGAs/w0/beach-boat-sunrise-scenery-seascape-17-4K.jpg",
                                "https://wallpaperaccess.com/full/1879118.jpg",
                                "https://g4.img-dpreview.com/5ADC4BE97EF846F2B04F94C0E90F0F0E.jpg"
                            )
                        )
                        CategoryState(data = it.result)

                    }
                }
            }
        }
        getBanners()
    }

    private fun getBanners() {
        viewModelScope.launch(Default) {
            /* FakeCategoryImpl().getBanners().collectLatest {
                 when (it) {
                     is Resource.Failure -> _categoryState.value = CategoryState(error = it.error)
                     Resource.Loading -> _categoryState.value = CategoryState(isLoading = true)

                 }
             }*/
        }
    }

    fun queryChanged(newQuery: String = "") {
        _categoryQuery.value = newQuery
    }

    fun querySubmitted() {

    }
}