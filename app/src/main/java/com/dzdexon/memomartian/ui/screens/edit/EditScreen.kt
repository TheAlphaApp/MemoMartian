package com.dzdexon.memomartian.ui.screens.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.EditNoteBody
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import kotlinx.coroutines.launch

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
    viewModelEdit: EditScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val tagList = viewModelEdit.tagList.collectAsState()
    val context = LocalContext.current
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
                modifier = Modifier.background(color = Color.Red),
                actions = {
                    IconButton(onClick = {
                        viewModelEdit.deleteNote().also {
                            Toast.makeText(
                                context, "Note Deleted Permanently",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        coroutineScope.launch {
                            navigateToHome()
                        }



                    }) {
                        Icon(Icons.Filled.DeleteForever, contentDescription = "Delete Icon")

                    }
                }
            )
        },
    ) { innerPadding ->
        EditNoteBody(
            note = viewModelEdit.note,
            onNoteValueChange = viewModelEdit::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModelEdit.updateNote()
                    navigateBack()
                }
            },
            tagList = tagList.value,
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
            modifier = modifier.padding(innerPadding),
            isNoteValid = viewModelEdit.validateInput(),
            createNewTag = viewModelEdit::createNewTag
        )

    }

}