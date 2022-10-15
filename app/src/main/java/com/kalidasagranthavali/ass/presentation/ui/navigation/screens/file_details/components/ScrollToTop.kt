package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.file_details.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BoxScope.ScrollToTopButton(
    listState: LazyListState,
    coroutineScope: CoroutineScope
) {
    val shouldShowButton = remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    androidx.compose.animation.AnimatedVisibility(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(5.dp),
        visible = shouldShowButton.value,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut(),

        content = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }, modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        })
}