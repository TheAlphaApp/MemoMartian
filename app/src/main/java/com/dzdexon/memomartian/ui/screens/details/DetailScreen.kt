package com.dzdexon.memomartian.ui.screens.details

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar

object DetailScreenDestination : NavigationDestination {
    override val route: String = "detail_screen"
    const val noteIdArgs = "noteId"
    val routeWithArgs = "$route/{$noteIdArgs}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navigateToEditScreen: (Long) -> Unit,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModelDetail: DetailScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val noteWithTagsModel by viewModelDetail.uiState.collectAsState()
    val note : Note? = noteWithTagsModel?.note
    note?.let {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            topBar = {
                NoteTopAppBar(
                    canNavigateBack = canNavigateBack,
                    navigateUp = navigateUp,
                    actions = {
                        TextButton(
                            onClick = { navigateToEditScreen(note.noteId) }) {
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

                    if (!note.imageUri.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = Uri.parse(note.imageUri))
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.3f)
                                .fillMaxWidth()
                                .padding(6.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(text = note.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = note.content, style = MaterialTheme.typography.bodyLarge)
                    noteWithTagsModel?.tags?.forEach { tag ->
                        FilterChip(
                            label = {
                                Text(text = tag.tagName)
                            },
                            selected = true, onClick = { })
                    }
                }
            }
        }
    }
}