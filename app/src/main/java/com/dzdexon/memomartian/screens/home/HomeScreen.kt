package com.dzdexon.memomartian.screens.home

//import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.component.NoteTopAppBar
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToCreateNote: () -> Unit,
    navigateToNoteDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        topBar = {
            NoteTopAppBar(canNavigateBack = false, title = "Memo Martian")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCreateNote,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Items",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },

        ) { innerPadding ->
        HomeBody(
            notesList = homeUiState.notesList,
            onNoteClick = navigateToNoteDetail,
            modifier = modifier.padding(innerPadding)
        )

    }

}

@Composable
fun HomeBody(
    notesList: List<Note>,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = notesList, key = { it.id }) { note ->
            Card(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    onNoteClick(note.id)
                }) {
                Column(Modifier.padding(16.dp)) {
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                    note.tags.forEach { tag ->
                        Text(text = tag, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }


        }
    }
}
