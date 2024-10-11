package com.dzdexon.memomartian.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.NoteCard
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.theme.ibmPlexMono

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

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
    Scaffold(
        containerColor = colors.primary,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = colors.primary,
                    scrolledContainerColor = colors.primary,
                    navigationIconContentColor = colors.onPrimary,
                    titleContentColor = colors.onPrimary,
                    actionIconContentColor = colors.onPrimary,
                ),
                title = {
                    Text(
                        text = "Notes",
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.ntype82_headline)),
                    )

                },
                actions = {
                    IconButton(onClick = navigateToSearchScreen) {
                        Icon(
                            painter = painterResource(R.drawable.search_white),
                            modifier = modifier.size(24.dp),
                            contentDescription = null
                        )
//                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = navigateToSearchScreen) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            modifier = modifier.size(24.dp),
                            contentDescription = "More Menu"
                        )
                    }
                }
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
                    // Add your navigation items here
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = colors.onPrimary,
                            selectedTextColor = colors.onPrimary,
                            selectedIndicatorColor = Color.Transparent,
                            unselectedIconColor = colors.onTertiary,
                            unselectedTextColor = colors.onTertiary,
                            disabledIconColor = colors.onTertiary,
                            disabledTextColor = colors.onTertiary
                        ),
                        selected = true,
                        onClick = { /* Handle home click */ },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.notes),
                                tint = colors.onSecondary,
                                contentDescription = null
                            )
                        }
                    )
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = colors.accent,
                            selectedTextColor = colors.accent,
                            selectedIndicatorColor = colors.onPrimary,
                            unselectedIconColor = colors.onTertiary,
                            unselectedTextColor = colors.onTertiary,
                            disabledIconColor = colors.onTertiary,
                            disabledTextColor = colors.onTertiary
                        ),
                        selected = false,
                        onClick = { /* Handle profile click */ },

                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.check_ring),
                                tint = colors.onSecondary,

                                contentDescription = null
                            )
                        }

                    )
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = colors.accent,
                            selectedTextColor = colors.accent,
                            selectedIndicatorColor = colors.onPrimary,
                            unselectedIconColor = colors.onTertiary,
                            unselectedTextColor = colors.onTertiary,
                            disabledIconColor = colors.onTertiary,
                            disabledTextColor = colors.onTertiary
                        ),
                        selected = false,
                        onClick = { /* Handle profile click */ },

                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.list_alt),
                                tint = colors.onSecondary,
                                contentDescription = null
                            )
                        }

                    )

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

val ALL_TAG = Tag(tagId = 420373, tagName = "all")

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
        modifier = modifier, verticalArrangement = Arrangement.Top
    ) {
        Box(Modifier.height(64.dp))
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
        if (selectedTag == ALL_TAG) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(horizontal = 8.dp),
                columns = StaggeredGridCells.Fixed(2),
            ) {
                items(items = notesList, key = { it.note.noteId }) { noteWithTags ->
                    NoteCard(
                        note = noteWithTags.note,
                        tagsList = noteWithTags.tags,
                        onClick = onNoteClick
                    )
                }
            }

        } else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(horizontal = 8.dp),
                columns = StaggeredGridCells.Fixed(2),
            ) {
                filteredItems?.let { tagWithNotes ->
                    items(items = tagWithNotes.notes, key = { it.noteId }) { note ->

                        NoteCard(
                            note = note, tagsList = notesList.first {
                                it.note.noteId == note.noteId
                            }.tags, onClick = onNoteClick
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
