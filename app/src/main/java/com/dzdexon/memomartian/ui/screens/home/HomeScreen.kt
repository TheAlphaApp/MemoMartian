package com.dzdexon.memomartian.ui.screens.home

//import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.ui.shared.component.CustomDialog
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToCreateNote: () -> Unit,
    navigateToNoteDetail: (Int) -> Unit,
    navigateToTagManageScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelHome: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelTag: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModelHome.homeUiState.collectAsState()
    val tagState by viewModelTag.tagState.collectAsState()
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
            tagsList = tagState.tagList
                .toMutableList().also {
                    it.add(0, Tag(tagName = "All"))
                },
            onNoteClick = navigateToNoteDetail,
            navigateToTagManageScreen =  navigateToTagManageScreen,
            modifier = modifier.padding(innerPadding),
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeBody(
    notesList: List<Note>,
    tagsList: List<Tag>,
    navigateToTagManageScreen: () -> Unit,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTag by rememberSaveable {
        mutableStateOf("All")
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = tagsList, key = { it.id }) { tag ->
                FilterChip(
                    label = {
                        Text(text = tag.tagName)
                    },
                    selected = tag.tagName == selectedTag,
                    onClick = {
                        selectedTag = tag.tagName
                    })
            }


        }

        AssistChip(
            modifier = Modifier.padding(horizontal = 8.dp),
            label = {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")

            },
            onClick = {
                navigateToTagManageScreen()
//                newTag = ""
//                showTagAddDialog = !showTagAddDialog

            })

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(8.dp),
            columns = StaggeredGridCells.Fixed(2),
        ) {
            items(items = notesList.filter { note ->
                if (selectedTag == "All") true
                else note.tags.contains(selectedTag)
            }, key = { it.id }) { note ->
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

}


