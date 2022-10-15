package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_to_sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.modals.FilesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubToSubCategoryViewModel @Inject constructor(
    private val subToSubCategoryRemoteRepository: SubToSubCategoryRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _subToSubCategoryState = MutableStateFlow(SubToSubCategoryState())

    val subToSubCategoryState = combine(_subToSubCategoryState, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SubToSubCategoryState())

    private val _fileState = MutableStateFlow(FilesState())

    val fileState = combine(_fileState, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FilesState())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            launch {
                getSubToSubCategoryData(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )
            }
            launch {
                getFilesData(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )
            }
        }
    }

    private suspend fun getSubToSubCategoryData(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getSubToSubCategories(catId, subCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    private suspend fun getFilesData(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getFiles(catId, subCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _fileState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _fileState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _fileState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _fileState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}