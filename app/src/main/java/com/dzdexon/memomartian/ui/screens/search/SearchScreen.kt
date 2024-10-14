package com.dzdexon.memomartian.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.shared.component.NemoMinimalTextField
import com.dzdexon.memomartian.ui.shared.component.NoteCard
import com.dzdexon.memomartian.ui.theme.LocalCustomColors


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    navigateToDetailScreen: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val colors = LocalCustomColors.current
    Scaffold(containerColor = colors.primary, contentColor = colors.onPrimary, topBar = {
        SearchBar(
            navigateUp = navigateUp, value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn {
                    items(
                        items = notes
                    ) { noteWithTags ->
                        val note = noteWithTags.note
                        NoteCard(
                            note = note,
                            showImages = false,
                            tagsList = noteWithTags.tags,
                            onClick = {
                                navigateToDetailScreen(note.noteId)
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(
    onTap: () -> Unit = {},
    navigateUp: () -> Unit = {},
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCustomColors.current
    Surface(
        color = colors.primary, modifier = Modifier.fillMaxWidth()
    ) {
        Box(

            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onTap()
                }
                .background(color = colors.secondary)


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.caret_left),
                        contentDescription = "Back Icon",
                        tint = colors.onPrimary,
                    )
                }
                NemoMinimalTextField(
                    value = value,
                    onValueChange = {
                        onValueChange(it)
                    },
                    placeholderText = "Search your notes",
                    modifier = modifier,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


