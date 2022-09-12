package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kalidasagranthavali.ass.domain.modals.Category

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CategoryItem(
    data: Category,
    onClick: (Int) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .padding(5.dp)
        .animateItemPlacement()
        .fillMaxWidth()
        .clickable { onClick(data.id) }) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = rememberAsyncImagePainter(model = data.image),
                contentDescription = null
            )
            Text(
                text = data.category,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}