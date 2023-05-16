package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.model.Tag


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteInputForm(
    noteUiState: NoteUiState,
    modifier: Modifier = Modifier,
    onValueChange: (NoteUiState) -> Unit = {},
    onSaveClick: () -> Unit,
    tagList: List<Tag>,
    navigateToTagManageScreen: () -> Unit,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
) {
    var showTagDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier = modifier.fillMaxWidth()) {

        TextField(
            value = noteUiState.title,
            onValueChange = { onValueChange(noteUiState.copy(title = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text("Title", style = MaterialTheme.typography.titleLarge) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.titleLarge
        )
        TextField(
            value = noteUiState.content,
            onValueChange = { onValueChange(noteUiState.copy(content = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text("Content", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent

            ),
            textStyle = MaterialTheme.typography.bodyLarge

        )
        TagView(
            tagList.filter { tag ->
                noteUiState.tags.contains(tag.id)
            }.map {
                it.tagName
            }
        )
        ElevatedButton(
            onClick = {
                showTagDialog = !showTagDialog
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "#Hashtags")
            }
        }

        if (noteUiState.isValid) Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Note")
        }
        if (showTagDialog) {
            CustomDialog(
                onDismissRequest = {
                    showTagDialog = !showTagDialog
                },
                primaryButtonText = "OK",
                primaryButtonEnabled = true,
                onPrimaryButtonClick = {
                    showTagDialog = !showTagDialog
                },
                secondaryButtonText = "Cancel",
                onSecondaryButtonClick = {
                    showTagDialog = !showTagDialog

                }
            ) {

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(100.dp)
                ) {
                    items(items = tagList, key = { it.id }) { tag ->
                        FilterChip(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            label = {
                                Text(text = tag.tagName)
                            },
                            selected = noteUiState.tags.contains(tag.id),
                            onClick = {
                                if (noteUiState.tags.contains(tag.id)) {
                                    removeTagFromNote(tag)
                                } else {
                                    addTagToNote(tag)
                                }
                            }
                        )
                    }


                }
                AssistChip(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    label = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        Text(text = "Manage Tags")
                    },
                    onClick = {
                        navigateToTagManageScreen()
                    })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagView(tagsList: List<String>) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = tagsList, key = { it }) { tag ->
            FilterChip(
                label = {
                    Text(text = tag)
                },
                selected = true,
                enabled = true,
                onClick = {

                }
            )
        }
    }
}


