package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.R


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
    val colors = LocalCustomColors.current
    Surface(
        modifier = Modifier.imePadding(),
        color = colors.secondary,
        contentColor = colors.onPrimary
    ) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle(color = colors.onTertiary) },
            containerColor = colors.secondary,
            contentColor = colors.onPrimary
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
        newTagString = ""
    }

    val colors = LocalCustomColors.current

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
                        IconButton(
                            onClick = {
                                invokeOnCreatingNewTag()
                            }

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.plus),
                                modifier = Modifier.size(32.dp),
                                contentDescription = "Add Button Icon",
                            )
                        }
                    }
                },

                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = colors.tertiary,
                    focusedTextColor = colors.onPrimary,
                    focusedTrailingIconColor = colors.onPrimary,
                    focusedPlaceholderColor= colors.onTertiary,
                    unfocusedPlaceholderColor = colors.onTertiary,
                    unfocusedContainerColor = colors.tertiary,
                    unfocusedTextColor = colors.onSecondary,
                    unfocusedTrailingIconColor = colors.onPrimary,
                    cursorColor = colors.onSecondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                )
            )

        }
        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            tagList.filter { tag ->
                selectedTagIDs.contains(tag.tagId)
            }.forEach { tag ->
                NemoFilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = "#${tag.tagName}",
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

        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            tagList.filter { tag ->
                !selectedTagIDs.contains(tag.tagId)
            }.forEach { tag ->
                NemoFilterChip(

                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    label = "#${tag.tagName}",
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


@Composable
fun NemoFilterChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    val colors = LocalCustomColors.current
    FilterChip(
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = colors.tertiary,
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        label = {
            Text(text = label , fontFamily = FontFamily(Font(R.font.ibm_plex_mono_regular)))
        },
        colors = FilterChipDefaults.filterChipColors().copy(
            containerColor = Color.Transparent,
            labelColor = colors.onSecondary,
            selectedContainerColor = colors.tertiary,
            selectedLabelColor = colors.onSecondary,
        ),
        selected = selected,
        onClick = {
            onClick()
        },
    )
}

