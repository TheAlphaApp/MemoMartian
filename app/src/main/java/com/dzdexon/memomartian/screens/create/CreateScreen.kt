package com.dzdexon.memomartian.screens.create

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.component.EditNoteBody
import com.dzdexon.memomartian.component.NoteTopAppBar
import com.dzdexon.memomartian.navigation.NavigationDestination
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
    viewModel: CreateScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            NoteTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    coroutineScope.launch {
                        viewModel.saveNote()
                        navigateUp()
                    }
                },
                title = "Create Your Note"
            )
        },
    ) { innerPadding ->
        EditNoteBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )

    }
}

