package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    canNavigateBack: Boolean,
    actions: @Composable () -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title ?: "") },
            modifier = modifier,

            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            },
            actions = {
                actions()
            }
        )
    } else {
        TopAppBar(
            title = { Text(title ?: "") },
            modifier = modifier,
            actions = {
                actions()
            }
        )
    }
}