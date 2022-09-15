package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FilesRemoteRepository
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
class FilesViewModel @Inject constructor(
    private val filesRemoteRepository: FilesRemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val fileLocalRepository: FileLocalRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val files: Flow<List<HomeFiles>> = query.flatMapLatest {
        fileLocalRepository.getFiles(
            it,
            savedStateHandle.get<Int>("cat_id") ?: 0,
            savedStateHandle.get<Int>("sub_cat_id") ?: 0
        )
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            when (
                val response = filesRemoteRepository.getFiles(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )) {
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    response.result.print()
                    fileLocalRepository.submitFiles(response.result)
                }
            }

        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}