package com.dzdexon.memomartian.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.screens.home.SearchBar
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.utils.HelperFunctions


object SearchScreenDestination : NavigationDestination {
    override val route: String = "search_screen"
}


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelTag: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToDetailScreen: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val tagState by viewModelTag.tagState.collectAsState()
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Scaffold(
        topBar = {
            SearchBar(editable = true, navigateUp = navigateUp) {
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeholder = {
                        Text(
                            "Search Your Notes",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
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
                    ) { note ->
                        Card(modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
                                navigateToDetailScreen(note.id)
                            }) {

                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = HelperFunctions.formatOffsetDateTime(note.lastUpdate)
                                        ?: "", style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                tagState.tagList.filter {
                                    note.tags.contains(
                                        it.id
                                    )
                                }.map { filteredTag ->
                                    filteredTag.tagName
                                }.forEach {
                                    Text(text = it, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

