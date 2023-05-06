package com.dzdexon.memomartian.screens.details

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
import com.dzdexon.memomartian.component.NoteTopAppBar
import com.dzdexon.memomartian.navigation.NavigationDestination

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
    viewModel: DetailScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val note = viewModel.uiState.collectAsState()
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
                .padding(innerPadding).padding(16.dp)
                .fillMaxWidth(),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(text = note.value.title, style = MaterialTheme.typography.titleMedium)
                Text(text = note.value.content, style = MaterialTheme.typography.bodyMedium)
                note.value.tags.forEach { tag ->
                    Text(text = tag, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}