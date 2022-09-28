package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.FileInputStream
import java.io.InputStream
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

    private val _text = MutableStateFlow("")

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
                    withContext(IO) {
                        readData(FileInputStream(result.result))
                    }
                }
            }
        }
    }

    private fun readData(inputStream: InputStream) {

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                when (event) {
                    XmlPullParser.START_TAG -> {}
                    XmlPullParser.TEXT -> _text.update { it + parser.text }
                    XmlPullParser.END_TAG -> {}
                }
                event = parser.next()
            }
        } catch (e: Exception) {
            _fileState.update {
                it.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText(if (e is XmlPullParserException) "The file is corrupted" else "Unable to display the file")
                )
            }
        }
    }

    fun updateQuery(newQuery: String = "") {
        _fileDataQuery.value = newQuery
    }

    @Composable
    fun getData(): Flow<AnnotatedString> {
        val span = SpanStyle(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.SemiBold,
            background = MaterialTheme.colorScheme.primaryContainer
        )
        return combine(_text, fileDataQuery) { text, query ->
            buildAnnotatedString {
                var start = 0
                while (text.indexOf(query, start, ignoreCase = true) != -1 && query.isNotBlank()) {
                    val firstIndex = text.indexOf(query, start, true)
                    val end = firstIndex + query.length
                    append(text.substring(start, firstIndex))
                    withStyle(style = span) {
                        append(text.substring(firstIndex, end))
                    }
                    start = end
                }
                append(text.substring(start, text.length))
                toAnnotatedString()
            }
        }
    }
}
