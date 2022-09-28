package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val filesRemoteRepository: FilesRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _state = MutableStateFlow(FilesState())

    val fileState = combine(_state, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query,ignoreCase = true) }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FilesState())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getSubCategoryData(
                savedStateHandle.get<Int>("cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_to_sub_cat_id") ?: 0,
            )
        }
    }

    private suspend fun getSubCategoryData(catId: Int, subCatId: Int, subToSubCatId: Int) {
        filesRemoteRepository.getFiles(catId, subCatId, subToSubCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _state.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _state.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _state.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _state.update { state ->
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