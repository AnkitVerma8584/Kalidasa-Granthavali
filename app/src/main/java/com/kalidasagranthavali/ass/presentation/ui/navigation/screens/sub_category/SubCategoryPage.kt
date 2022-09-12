package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.sub_category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubCategoryPage(
    onNavigateToFile: (Int) -> Unit
) {
    Text(text = "Sub category", modifier = Modifier
        .padding(16.dp)
        .clickable {
            onNavigateToFile(0)
        })

}