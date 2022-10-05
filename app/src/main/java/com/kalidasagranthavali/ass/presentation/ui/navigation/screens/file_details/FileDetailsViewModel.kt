package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FileDataRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FileDetailsViewModel @Inject constructor(
    private val filesRepository: FileDataRemoteRepository,
    private val fileLocalRepository: FileLocalRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _fileState = MutableStateFlow(FileDataState())
    val fileState = _fileState.asStateFlow()

    private val _fileDataQuery = MutableStateFlow("")
    val fileDataQuery get() = _fileDataQuery.asStateFlow()

    private val _text = MutableStateFlow(listOf<String?>())
    val text = _text.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            fetchFile(fileLocalRepository.getFileById(savedStateHandle.get<Int>("file_id") ?: 0))
        }
    }

    private suspend fun fetchFile(homeFiles: HomeFiles) {
        filesRepository.getFileData(homeFiles).collectLatest { result ->
            when (result) {
                is Resource.Cached -> Unit
                is Resource.Failure -> {
                    _fileState.update {
                        it.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _fileState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
                is Resource.Success -> {
                    _fileState.update {
                        it.copy(isLoading = false, error = null)
                    }
                    readTextFile(result.result)
                }
            }
        }
    }

    private fun readTextFile(file: File) {
        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            val text = mutableListOf<String?>()
            while (br.readLine().also { line = it } != null) {
                text.add(line)
            }
            br.close()
            _text.update { text.toList() }
        } catch (e: Exception) {
            _fileState.update {
                it.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText(if (e is IOException) "The file is corrupted" else "Unable to display the file")
                )
            }
        }
    }

    fun updateQuery(newQuery: String = "") {
        _fileDataQuery.value = newQuery
    }
}
