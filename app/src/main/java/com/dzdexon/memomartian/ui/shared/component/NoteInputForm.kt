package com.dzdexon.memomartian.ui.shared.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dzdexon.memomartian.NotesApplication
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.ui.screens.managetags.TagManageBottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputForm(
    noteUiState: NoteUiState,
    modifier: Modifier = Modifier,
    onValueChange: (NoteUiState) -> Unit = {},
    onSaveClick: () -> Unit,
    tagList: List<Tag>,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
) {
    var showTagDialog by rememberSaveable {
        mutableStateOf(false)
    }


// In this example, the app lets the user select up to 5 media files.
    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->

            if (!uris.isNullOrEmpty()) {
                uris.forEach { uri ->
                    NotesApplication.getUriPermission(uri)
                }
                var imageString: String? = null
                uris.forEach { str ->
                    imageString = if (imageString != null) {
                        "$imageString,$str"
                    } else {
                        str.toString()
                    }
                    print(imageString.toString())

                }
                val oldString = noteUiState.imageUri
                val newString = "$oldString,$imageString"
                onValueChange(
                    noteUiState.copy(imageUri = newString)
                )
            }
        }

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        TagManageBottomSheet(
            addTagToNote = addTagToNote,
            removeTagFromNote = removeTagFromNote,
            selectedTags = noteUiState.tags,
        ) {
            showSheet = false
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        if (!noteUiState.imageUri.isNullOrEmpty()) {
            val imageList = noteUiState.imageUri.split(",").toTypedArray()
            imageList.forEach { imageStr ->
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = Uri.parse(imageStr))
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(6.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }

        TextField(
            value = noteUiState.title,
            onValueChange = { onValueChange(noteUiState.copy(title = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text("Title", style = MaterialTheme.typography.titleLarge) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
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
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
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
                showSheet = true
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "#Hashtags")
            }
        }
        ElevatedButton(
            onClick = {
                pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Add Image")
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