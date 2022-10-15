package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.kalidasagranthavali.ass.domain.repository.local.FileLocalRepository
import com.kalidasagranthavali.ass.domain.repository.remote.FileDataRemoteRepository
import com.kalidasagranthavali.ass.domain.utils.Resource
import com.kalidasagranthavali.ass.domain.utils.StringUtil
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDataState
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.modals.FileDocumentText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
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
            fetchFile(
                fileLocalRepository.getFileById(savedStateHandle.get<Int>("file_id") ?: 0)
            )
        }
    }

    private suspend fun fetchFile(homeFiles: HomeFiles?) {
        homeFiles?.let {
            filesRepository.getFileData(homeFiles).collectLatest { result ->
                when (result) {
                    is Resource.Cached -> {
                        _fileState.update {
                            it.copy(isLoading = false, error = null)
                        }
                        readTextFile(result.result)
                    }
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
        } ?: _fileState.update {
            it.copy(isLoading = false, error = StringUtil.DynamicText("Unable to load file"))
        }
    }

    private fun readTextFile(file: File) {
        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            val text = mutableListOf<String?>()
            val paragraph = StringBuilder()
            var i = 0
            while (br.readLine().also { line = it } != null) {
                paragraph.append(line)
                if (i == 2) {
                    text.add(paragraph.toString())
                    paragraph.clear()
                    i = 0
                } else paragraph.append("\n")
                i++
            }
            if (paragraph.isNotBlank())
                text.add(paragraph.toString())
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

    val searchedText = combine(fileDataQuery, text) { query, list ->
        list.mapIndexed { index, s ->
            FileDocumentText(index, s)
        }.filter { s ->
            s.text?.contains(query, ignoreCase = true) ?: false
        }
    }.flowOn(Default).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun updateQuery(newQuery: String = "") {
        _fileDataQuery.value = newQuery
    }
}
