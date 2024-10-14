package com.dzdexon.memomartian.ui.screens.edit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import com.dzdexon.memomartian.ui.theme.LocalCustomColors

object EditScreenDestination : NavigationDestination {
    override val route: String = "edit_screen"
    const val noteIdArgs = "noteId"
    val routeWithArgs = "$route/{$noteIdArgs}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    viewModelEdit: EditScreenViewModel = hiltViewModel<EditScreenViewModel>()
) {
    Log.d("NEMO: EDITSCREEN", "REBUILD EDIT Screen")
    val uiState by viewModelEdit.uiState
    val colors = LocalCustomColors.current
    Scaffold(
        containerColor = colors.primary,
        contentColor = colors.onPrimary,
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    navigateUp()
                },
                title = "Edit Your Note",
                modifier = Modifier.background(color = Color.Red),
                actions = {
                    IconButton(onClick = {
                        viewModelEdit.deleteNote(navigateToHome)
//                            .also {
//                            Toast.makeText(
//                                context, "Note Deleted Permanently",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                        navigateToHome()
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
            NoteInputForm(
                selectedTags = uiState.selectedTags,
                note = uiState.note,
                viewModelEdit = viewModelEdit,
                addTagToNote = { tag ->
                    viewModelEdit.updateTagInNote(tag)
                },
                removeTagFromNote = { tag ->
                    viewModelEdit.updateTagInNote(tag, remove = true)
                },
                createNewTag = viewModelEdit::createNewTag,
                modifier = modifier.padding(innerPadding),
                tagList = uiState.allTags,
            )
        }
    }
}
