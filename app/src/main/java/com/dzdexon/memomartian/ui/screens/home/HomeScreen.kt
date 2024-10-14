package com.dzdexon.memomartian.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.NoteCard
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import com.dzdexon.memomartian.ui.theme.ibmPlexMono
import com.dzdexon.memomartian.utils.ALL_TAG

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}


data class NavBarItem(val name: String, val icon: Int, val selectedIcon: Int)

val navBarItems = listOf(
    NavBarItem(name = "Notes", icon = R.drawable.notepad, selectedIcon = R.drawable.notepad_fill),
    NavBarItem(
        name = "Lists",
        icon = R.drawable.list_bullets,
        selectedIcon = R.drawable.list_bullets_fill
    ),
    NavBarItem(
        name = "Todos",
        icon = R.drawable.check_square,
        selectedIcon = R.drawable.check_square_fill
    ),
    NavBarItem(
        name = "kanban",
        icon = R.drawable.kanban,
        selectedIcon = R.drawable.kanban_fill
    )

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToEditNote: (Long) -> Unit,
    navigateToDetailScreen: (Long) -> Unit,
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelHome: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val listOfNotes by viewModelHome.stateFlowOfListOfNotes.collectAsState()
    val tagList by viewModelHome.stateFlowOfListOfTags.collectAsState()
    val colors = LocalCustomColors.current
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        containerColor = colors.primary,
        topBar = {
            NoteTopAppBar(
                title = "Nemo",
                actions = {
                    IconButton(onClick = navigateToSearchScreen) {
                        Icon(
                            painter = painterResource(R.drawable.magnifying_glass),
                            modifier = modifier.size(24.dp),
                            contentDescription = null
                        )
//                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = navigateToSearchScreen) {
                        Icon(
                            painter = painterResource(R.drawable.dots_three_outline_vertical),
                            modifier = modifier.size(24.dp),
                            contentDescription = "More Menu"
                        )
                    }
                },
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(
                        top = 0.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ) // This adds padding around the navigation bar
                    .background(Color.Transparent) // Make the padding transparent
                    .clip(RoundedCornerShape(16.dp)) // Clip to rounded corners
            ) {
                NavigationBar(
                    containerColor = colors.tertiary,
                    contentColor = colors.onTertiary,
                    modifier = Modifier
                        .height(64.dp)
                        .background(colors.tertiary) // The color of the navigation bar
                        .fillMaxWidth() // Make it fill the width
                ) {
                    navBarItems.forEachIndexed { index, navBarItem ->
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = colors.onPrimary,
                                selectedTextColor = colors.onPrimary,
                                selectedIndicatorColor = Color.Transparent,
                                unselectedIconColor = colors.onSecondary,
                                unselectedTextColor = colors.onSecondary,
                                disabledIconColor = colors.onTertiary,
                                disabledTextColor = colors.onTertiary
                            ),
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(
                                    painter = painterResource(if (selectedIndex == index) navBarItem.selectedIcon else navBarItem.icon),
                                    modifier = modifier.size(32.dp),
                                    contentDescription = navBarItem.name
                                )
                            }
                        )
                    }


                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = colors.accent,
                contentColor = colors.onPrimary,
                onClick = {
                    viewModelHome.createNewNote(navigateToEditNote)
                },
                modifier = Modifier.navigationBarsPadding(),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create New Note",

                    )

            }

        },

        ) { innerPadding ->
        Log.d("NEMO: Top Padding", "Top Padding: ${innerPadding.calculateTopPadding()}")
        Log.d("NEMO: Bottom Padding", "Bottom Padding: ${innerPadding.calculateBottomPadding()}")

        HomeBody(
            notesList = listOfNotes,
            tagsList = tagList,
            onNoteClick = navigateToDetailScreen,
            modifier = modifier,
            viewModelHome = viewModelHome,
            innerPadding = innerPadding
        )

    }

}


@Composable
fun HomeBody(
    notesList: List<NoteWithTagsModel>,
    tagsList: List<Tag>,
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModelHome: HomeViewModel,
    innerPadding: PaddingValues
) {
    var selectedTag by remember {
        mutableStateOf(ALL_TAG)
    }


    val filteredItems: TagWithNotesModel? by viewModelHome.stateFlowTagWithNotes(selectedTag.tagId)
        .collectAsState()
    val newTagsList = listOf(ALL_TAG) + tagsList
    val colors = LocalCustomColors.current
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding(), bottom = 0.dp)
            .fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            items(items = newTagsList, key = { it.tagId }) { tag ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            onClick = {
                                selectedTag = tag
                            }
                        )
                        .background(
                            color =
                            if (selectedTag == tag) {
                                colors.accent
                            } else {
                                Color.Transparent
                            }
                        )

                ) {
                    Text(
                        text = "#" + tag.tagName,
                        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = colors.onPrimary,
                        fontFamily = ibmPlexMono
                    )
                }

            }


        }
        Box(modifier.height(8.dp))

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(horizontal = 8.dp),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 16.dp)
        ) {

            if (selectedTag == ALL_TAG) {
                items(items = notesList, key = { it.note.noteId }) { noteWithTags ->
                    NoteCard(
                        note = noteWithTags.note,
                        tagsList = noteWithTags.tags,
                        onClick = onNoteClick,
                        modifier = modifier
                    )
                }
            } else {
                filteredItems?.let { tagWithNotes ->
                    items(items = tagWithNotes.notes, key = { it.noteId }) { note ->
                        NoteCard(
                            note = note, tagsList = notesList.first {
                                it.note.noteId == note.noteId
                            }.tags, onClick = onNoteClick,
                            modifier = modifier
                        )
                    }
                }
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


@Composable
fun FloatingBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(32.dp)
            .background(Color.Cyan)
            .clickable { /* Handle click for a specific action */ },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Home", color = Color.White)
            Text(text = "Search", color = Color.White)
            Text(text = "Profile", color = Color.White)
        }
    }
}
