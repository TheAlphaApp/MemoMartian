package com.dzdexon.memomartian.ui.shared.component


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Tag


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManageBottomSheet(
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    selectedTags: List<Tag>,
    tagList: List<Tag>,
    createNewTag: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    Surface(
        modifier = Modifier.imePadding()
    ) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            TagSelectionScreen(
                addTagToNote = addTagToNote,
                selectedTags = selectedTags,
                removeTagFromNote = removeTagFromNote,
                tagList = tagList,
                createNewTag = createNewTag,
            )
        }
    }
}


@OptIn(
    ExperimentalLayoutApi::class,
)
@Composable
private fun TagSelectionScreen(
    tagList: List<Tag>,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    createNewTag: (String) -> Unit,
    selectedTags: List<Tag>,
) {
    var newTagString by remember {
        mutableStateOf("")
    }
    val selectedTagIDs = selectedTags.map { it.tagId }
    fun invokeOnCreatingNewTag() {
        createNewTag(newTagString)
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            TextField(
                value = newTagString,
                onValueChange = {
                    newTagString = it
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        invokeOnCreatingNewTag()
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
                                invokeOnCreatingNewTag()
                            }

                        ) {
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
                selectedTagIDs.contains(tag.tagId)
            }.forEach { tag ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = {
                        Text(text = "#${tag.tagName}")
                    },
                    selected = selectedTagIDs.contains(tag.tagId),
                    onClick = {
                        if (selectedTagIDs.contains(tag.tagId)) {
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
                !selectedTagIDs.contains(tag.tagId)
            }.forEach { tag ->
                FilterChip(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    label = {
                        Text(text = "#${tag.tagName}")
                    },
                    selected = selectedTagIDs.contains(tag.tagId),
                    onClick = {
//                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
//                            showTagUpdateDialog = true
//                            updatingTag = tag
                        if (selectedTagIDs.contains(tag.tagId)) {
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