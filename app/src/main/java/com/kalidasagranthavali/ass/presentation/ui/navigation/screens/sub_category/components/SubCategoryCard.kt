package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kalidasagranthavali.ass.domain.modals.HomeSubCategory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.SubCategoryCard(
    data: HomeSubCategory,
    onClick: (HomeSubCategory) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .padding(5.dp)
        .animateItemPlacement()
        .fillMaxWidth()
        .clickable { onClick(data) }) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = data.image),
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = data.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }
        }

    }
}

@Preview
@Composable
fun SubCategoryCardPreview() {
    LazyColumn {
        item {
            SubCategoryCard(
                data = HomeSubCategory(1, 2, "Beaches", "Some basic description", ""),
                onClick = {})
        }
    }
}