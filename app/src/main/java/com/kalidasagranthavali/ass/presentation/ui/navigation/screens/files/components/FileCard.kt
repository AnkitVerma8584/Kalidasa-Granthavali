package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.files.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kalidasagranthavali.ass.data.remote.Api.getDocumentUrl
import com.kalidasagranthavali.ass.domain.modals.HomeFiles
import com.rajat.pdfviewer.PdfViewerActivity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.FileCard(
    item: HomeFiles,
    onFileClicked: (homeFiles: HomeFiles, query: String, index: Int) -> Unit,
    context: Context = LocalContext.current
) {
    ElevatedCard(modifier = Modifier
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .animateItemPlacement()
        .fillMaxWidth()
        .clickable {
            if (item.isNotPdf)
                onFileClicked(item, "", -1)
            else
                context.startActivity(
                    PdfViewerActivity.launchPdfFromUrl(
                        context, item.file_url.getDocumentUrl(),
                        item.name, "", enableDownload = false
                    )
                )
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            if (item.description.isNotBlank())
                Text(
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
        }
    }

}