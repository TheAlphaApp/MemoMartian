//package com.dzdexon.memomartian.ui.screens.create
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.dzdexon.memomartian.AppViewModelProvider
//import com.dzdexon.memomartian.navigation.NavigationDestination
//import com.dzdexon.memomartian.ui.screens.edit.EditScreenViewModel
//import com.dzdexon.memomartian.ui.shared.component.EditNoteBody
//import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
//import kotlinx.coroutines.launch
//
//object CreateScreenDestination : NavigationDestination {
//    override val route: String = "createScreen"
//}
//
//
//@Composable
//fun CreateScreen(
//    modifier: Modifier = Modifier,
//    navigateBack: () -> Unit,
//    navigateUp: () -> Unit,
//    canNavigateBack: Boolean = true,
//    viewModelEdit: EditScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
//) {
//    val coroutineScope = rememberCoroutineScope()
//    val tagList = viewModelEdit.tagList.collectAsState()
//    Scaffold(
//        topBar = {
//            NoteTopAppBar(
//                canNavigateBack = canNavigateBack,
//                navigateUp = {
//                    coroutineScope.launch {
//                        viewModelEdit.updateNote()
//                        navigateUp()
//                    }
//                },
//                title = "Create Your Note"
//            )
//        },
//    ) { innerPadding ->
//        viewModelEdit.noteWithTagsModel?.let {
//            EditNoteBody(
//                noteWithTagsModel = it,
//                onNoteValueChange = viewModelEdit::updateUiState,
//                onSaveClick = {
//                    coroutineScope.launch {
//                        viewModelEdit.updateNote()
//                        navigateBack()
//                    }
//                },
//                addTagToNote = { tag ->
//                    coroutineScope.launch {
//                        viewModelEdit.updateTagInNote(tag)
//                    }
//                },
//                removeTagFromNote = { tag ->
//                    coroutineScope.launch {
//                        viewModelEdit.updateTagInNote(tag, remove = true)
//                    }
//                },
//                tagList = tagList.value,
//                isNoteValid = viewModelEdit.validateInput(),
//                modifier = modifier.padding(innerPadding),
//                createNewTag = viewModelEdit::createNewTag
//            )
//        }
//
//    }
//}
//
