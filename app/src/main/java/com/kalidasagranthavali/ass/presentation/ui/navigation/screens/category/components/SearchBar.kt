package com.kalidasagranthavali.ass.presentation.ui.navigation.screens.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    query: String,
    onClearPressed: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {}
) {
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onClearPressed()
            },
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = null
            )
        }
    }

    val gradient = listOf(
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 1f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(gradient)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = MaterialTheme.colorScheme.background,
                unfocusedBorderColor = MaterialTheme.colorScheme.background
            ),
            modifier = modifier
                .fillMaxWidth(0.9f)
                .focusRequester(focusRequester),
            value = query,
            placeholder = {
                Text(hint, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            },
            trailingIcon = if (query.isNotBlank()) trailingIconView else null,
            singleLine = true,
            onValueChange = {
                onSearchQueryChanged(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.height(22.dp))
    }

}
