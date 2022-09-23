package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory
import com.kalidasagranthavali.ass.domain.repository.local.SubCategoryLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.SubCategoryRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubCategoryViewModel @Inject constructor(
    private val subCategoryRemoteRepository: SubCategoryRemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val subCategoryLocalRepository: SubCategoryLocalRepository
) :
    ViewModel() {

    private val _subCategoryQuery = MutableStateFlow("")
    val subCategoryQuery get() = _subCategoryQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val subCategories: Flow<List<HomeSubCategory>> = subCategoryQuery.flatMapLatest {
        subCategoryLocalRepository.getSubCategories(it, savedStateHandle.get<Int>("cat_id") ?: 0)
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            when (
                val response = subCategoryRemoteRepository.getSubCategories(
                    savedStateHandle.get<Int>("cat_id") ?: 0
                )) {
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    response.result.print()
                    subCategoryLocalRepository.submitSubCategories(response.result)
                }
                is Resource.Cached -> {

                }
            }

        }
    }

    fun queryChanged(newQuery: String = "") {
        _subCategoryQuery.value = newQuery
    }
}