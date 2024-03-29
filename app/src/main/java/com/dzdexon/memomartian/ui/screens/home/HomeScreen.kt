package com.dzdexon.memomartian.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.ui.shared.component.NoteCard

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToCreateNote: () -> Unit,
    navigateToDetailScreen: (Int) -> Unit,
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelHome: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelTag: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val listOfNotes by viewModelHome.stateFlowOfListOfNotes.collectAsState()
    val tagList by viewModelTag.tagList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    Text(text = "Notes")
                },
                actions = {
                    IconButton(onClick = navigateToSearchScreen) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCreateNote, modifier = Modifier.navigationBarsPadding()
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
            notesList = listOfNotes,
            tagsList = tagList,
            onNoteClick = navigateToDetailScreen,
            modifier = modifier.padding(innerPadding),
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(
    notesList: List<Note>,
    tagsList: List<Tag>,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val ALL_TAG = Tag(id = 420373, tagName = "All")
    var selectedTag by remember {
        mutableStateOf(ALL_TAG)
    }
    val newTagsList = listOf(ALL_TAG) + tagsList
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Top
    ) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = newTagsList, key = { it.id }) { tag ->
                FilterChip(label = {
                    Text(text = tag.tagName)
                }, selected = tag == selectedTag, onClick = {
                    selectedTag = tag
                })
            }


        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(horizontal = 8.dp),
            columns = StaggeredGridCells.Fixed(2),
        ) {
            items(items = notesList.filter { note ->
                if (selectedTag == ALL_TAG) true
                else note.tags.contains(selectedTag.id)
            }, key = { it.id }) { note ->
                NoteCard(
                    note = note, tagsList = tagsList, onClick = onNoteClick
                )
            }
        }
    }

}

@Composable
fun SearchBar(
    editable: Boolean = false,
    onTap: () -> Unit = {},
    navigateUp: () -> Unit = {},
    editableContent: @Composable () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Box(modifier =
        if (editable) {
            Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)

                .clickable {
                    onTap()
                }
                .background(color = MaterialTheme.colorScheme.primaryContainer)

        } else {
            Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(shape = RoundedCornerShape(40.dp))
                .clickable {
                    onTap()
                }
                .background(color = MaterialTheme.colorScheme.primaryContainer)

        }


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (editable) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                } else {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                }
                if (editable) {
                    editableContent()
                } else {
                    TextField(
                        enabled = false,
                        value = "",
                        onValueChange = { },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = {
                            Text(
                                "Search Your Notes", style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

