package com.dzdexon.memomartian.ui.screens.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.ui.shared.component.EditNoteBody
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import kotlinx.coroutines.launch

object EditScreenDestination : NavigationDestination {
    override val route: String = "edit_screen"
    const val noteIdArgs = "noteId"
    val routeWithArgs = "$route/{$noteIdArgs}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    viewModelEdit: EditScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelTag: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val tagState = viewModelTag.tagState.collectAsState()
    Scaffold(
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    coroutineScope.launch {
                        viewModelEdit.updateNote()
                        navigateUp()
                    }
                },
                title = "Edit Your Note",
                modifier = Modifier.background(color = Color.Red)
            )
        },
    ) { innerPadding ->
        EditNoteBody(
            noteUiState = viewModelEdit.noteUiState,
            onNoteValueChange = viewModelEdit::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModelEdit.updateNote()
                    navigateBack()
                }
            },
            tagList = tagState.value.tagList,
            addTagToNote = { tag ->
                coroutineScope.launch {
                    viewModelEdit.updateTagInNote(tag)
                }
            },
            removeTagFromNote = { tag ->
                coroutineScope.launch {
                    viewModelEdit.updateTagInNote(tag, remove = true)
                }
            },
            modifier = modifier.padding(innerPadding)
        )

    }

}