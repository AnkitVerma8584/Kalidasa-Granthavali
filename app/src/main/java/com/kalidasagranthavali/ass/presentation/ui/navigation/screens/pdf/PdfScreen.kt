package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.barteksc.pdfviewer.PDFView
import com.kalidasagranthavali.ass.presentation.ui.navigation.screens.common.Loading

@Composable
fun PdfScreen(
    pdfViewModel: PdfViewModel = hiltViewModel()
) {
    val pdfState by pdfViewModel.pdfState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        if (pdfState.isLoading)
            Loading()
        pdfState.error?.let {
            Text(
                text = it.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        pdfState.file?.let { pdf ->
            AndroidView(factory = {
                PDFView(it, null).also { pdfView ->
                    pdfView.fromFile(pdf).onError {
                        pdfViewModel.pdfState
                    }.load()
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}