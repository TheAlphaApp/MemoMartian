package com.dzdexon.memomartian.ui.screens.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.EditNoteBody
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar

object EditScreenDestination : NavigationDestination {
    override val route: String = "edit_screen"
    const val noteIdArgs = "noteId"
    val routeWithArgs = "$route/{$noteIdArgs}"
}


@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    viewModelEdit: EditScreenViewModel = hiltViewModel<EditScreenViewModel>()
) {
    val uiState by viewModelEdit.uiState
    val context = LocalContext.current
    Scaffold(
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    viewModelEdit.saveNote()
                    navigateUp()
                },
                title = "Edit Your Note",
                modifier = Modifier.background(color = Color.Red),
                actions = {
                    IconButton(onClick = {
                        viewModelEdit.deleteNote().also {
                            Toast.makeText(
                                context, "Note Deleted Permanently",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        navigateToHome()
                    }) {
                        Icon(Icons.Filled.DeleteForever, contentDescription = "Delete Icon")

                    }
                }
            )
        },
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Center the progress indicator
            ) {
                CircularProgressIndicator()
            }
        } else {
//            if (uiState.error != null) {
//                Toast.makeText(context, uiState.error, Toast.LENGTH_LONG).show()
//            }
//            if (uiState.note.noteId == 0L) {
//                Toast.makeText(context, "Note not found", Toast.LENGTH_LONG).show()
//            }

            EditNoteBody(
                selectedTags = uiState.selectedTags,
                note = uiState.note,
                viewModelEdit = viewModelEdit,
                onSaveClick = {
                    viewModelEdit.saveNote()
                    navigateBack()
                },
                allTags = uiState.allTags,
                addTagToNote = { tag ->
                    viewModelEdit.updateTagInNote(tag)
                },
                removeTagFromNote = { tag ->
                    viewModelEdit.updateTagInNote(tag, remove = true)
                },
                createNewTag = viewModelEdit::createNewTag,
                modifier = modifier.padding(innerPadding),
            )
        }
    }
}
