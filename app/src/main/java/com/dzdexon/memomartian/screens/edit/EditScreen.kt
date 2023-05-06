package com.dzdexon.memomartian.screens.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.component.EditNoteBody
import com.dzdexon.memomartian.component.NoteTopAppBar
import com.dzdexon.memomartian.navigation.NavigationDestination
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
    viewModel: EditScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    coroutineScope.launch {
                        viewModel.updateNote()
                        navigateUp()
                    }
                },
                title = "Edit Your Note",
                modifier = Modifier.background(color = Color.Red)
            )
        },
    ) { innerPadding ->
        EditNoteBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateNote()
                    navigateBack()
                }
            },

            modifier = modifier.padding(innerPadding)
        )

    }

}