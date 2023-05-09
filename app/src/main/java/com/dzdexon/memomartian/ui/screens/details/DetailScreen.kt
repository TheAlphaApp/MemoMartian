package com.dzdexon.memomartian.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel

object DetailScreenDestination : NavigationDestination {
    override val route: String = "detail_screen"
    const val noteIdArgs = "noteId"
    val routeWithArgs = "$route/{$noteIdArgs}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navigateToEditScreen: (Int) -> Unit,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModelDetail: DetailScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelTag: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val note = viewModelDetail.uiState.collectAsState()
    val tagList = viewModelTag.tagState.collectAsState()
    Scaffold(
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                actions = {
                    TextButton(
                        onClick = { navigateToEditScreen(note.value.id) }) {
                        Text(text = "Edit")
                    }
                }
            )
        },
    ) { innerPadding ->
        Card(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(text = note.value.title, style = MaterialTheme.typography.titleMedium)
                Text(text = note.value.content, style = MaterialTheme.typography.bodyMedium)
                tagList.value.tagList.filter { tag ->
                    note.value.tags.contains(tag.id)
                }.map {
                    it.tagName
                }.forEach { tag ->
                    Text(text = tag, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}