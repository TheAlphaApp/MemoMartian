package com.dzdexon.memomartian.ui.screens.create

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.ui.shared.component.EditNoteBody
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import kotlinx.coroutines.launch

object CreateScreenDestination : NavigationDestination {
    override val route: String = "createScreen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateToTagManageScreen: () -> Unit,
    viewModelCreate: CreateScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
                        viewModelCreate.saveNote()
                        navigateUp()
                    }
                },
                title = "Create Your Note"
            )
        },
    ) { innerPadding ->
        EditNoteBody(
            noteUiState = viewModelCreate.noteUiState,
            onNoteValueChange = viewModelCreate::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModelCreate.saveNote()
                    navigateBack()
                }
            },
            addTagToNote = { tag ->
                coroutineScope.launch {
                    viewModelCreate.updateTagInNewNote(tag)
                }
            },
            removeTagFromNote = { tag ->
                coroutineScope.launch {
                    viewModelCreate.updateTagInNewNote(tag, remove = true)
                }
            },
            tagList = tagState.value.tagList,
            navigateToTagManageScreen = navigateToTagManageScreen,
            modifier = modifier.padding(innerPadding)
        )

    }
}

