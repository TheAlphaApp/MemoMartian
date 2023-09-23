package com.dzdexon.memomartian.ui.screens.managetags


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.data.entities.Tag
import com.dzdexon.memomartian.ui.shared.component.CustomDialog
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManageBottomSheet(
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    selectedTags: List<Int>,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        TagSelectionScreen(
            addTagToNote = addTagToNote,
            selectedTags = selectedTags,
            removeTagFromNote = removeTagFromNote,
        )
    }
}


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
)
@Composable
private fun TagSelectionScreen(
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    selectedTags: List<Int>,
    viewModel: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagState = viewModel.tagState.collectAsState()
    val tagList = tagState.value.tagList
    val coroutineScope = rememberCoroutineScope()
    var newTagString by remember {
        mutableStateOf("")
    }
    val haptics = LocalHapticFeedback.current
    val context = LocalContext.current

    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            TextField(
                value = newTagString,
                onValueChange = {
                    newTagString = it
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            viewModel.createNewTag(newTagString)
                        }.invokeOnCompletion {
                            newTagString = ""
                            Toast.makeText(
                                context, "New tag added",
                                Toast.LENGTH_LONG
                            ).show()
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)

                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = { Text("Add New Tag") },
                singleLine = true,
                trailingIcon =
                {
                    if (newTagString.isNotBlank() && newTagString.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.createNewTag(newTagString)
                                }.invokeOnCompletion {
                                    newTagString = ""
                                    Toast.makeText(
                                        context, "New tag added",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)

                                }
                            }) {
                            Text("+ Add Tag")
                        }
                    }
                }
            )

        }
        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            tagList.filter { tag ->
                selectedTags.contains(tag.id)
            }.forEach { tag ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = {
                        Text(text = "#${tag.tagName}")
                    },
                    selected = selectedTags.contains(tag.id),
                    onClick = {
                        if (selectedTags.contains(tag.id)) {
                            removeTagFromNote(tag)
                        } else {
                            addTagToNote(tag)
                        }
                    },
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            tagList.filter { tag ->
                !selectedTags.contains(tag.id)
            }.forEach { tag ->
                FilterChip(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    label = {
                        Text(text = "#${tag.tagName}")
                    },
                    selected = selectedTags.contains(tag.id),
                    onClick = {
//                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
//                            showTagUpdateDialog = true
//                            updatingTag = tag
                        if (selectedTags.contains(tag.id)) {
                            removeTagFromNote(tag)
                        } else {
                            addTagToNote(tag)
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun TagUpdateDialog(
    onDismiss: () -> Unit,
    tag: Tag,
    viewModel: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var newTagString by remember {
        mutableStateOf(tag.tagName)
    }
    val coroutineScope = rememberCoroutineScope()

    CustomDialog(
        onDismissRequest = onDismiss,
        primaryButtonEnabled = false,
        onPrimaryButtonClick = {},
        secondaryButtonText = "Cancel",
        onSecondaryButtonClick = onDismiss
    ) {
        Column {
            TextField(
                value = newTagString,
                onValueChange = {
                    newTagString = it
                },
                placeholder = {
                    Text(text = "Tag Name")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            ElevatedButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateTag(tag, newTagString)
                    }.invokeOnCompletion {
                        onDismiss()
                    }

                }
            ) {
                Text(text = "Update")
            }
            ElevatedButton(
                onClick = { /*TODO*/
                    onDismiss()
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(text = "Delete")
            }
        }
    }
}