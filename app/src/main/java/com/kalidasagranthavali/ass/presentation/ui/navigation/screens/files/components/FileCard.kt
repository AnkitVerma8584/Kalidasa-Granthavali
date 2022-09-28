package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kalidasagranthavali.ass.data.remote.Api.getDocumentUrl
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.rajat.pdfviewer.PdfViewerActivity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.FileCard(
    item: HomeFiles,
    textBackground: List<Color>,
    onFileClicked: (HomeFiles) -> Unit,
    context: Context = LocalContext.current
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .animateItemPlacement()
            .fillMaxWidth()
            .aspectRatio(2 / 3f)
            .clickable {
                if (item.isPdf)
                    context.startActivity(
                        PdfViewerActivity.launchPdfFromUrl(
                            context,
                            item.file_url.getDocumentUrl(),
                            item.name,
                            "",
                            enableDownload = false
                        )
                    )
                else
                    onFileClicked(item)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(textBackground))
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )
        }
    }

}