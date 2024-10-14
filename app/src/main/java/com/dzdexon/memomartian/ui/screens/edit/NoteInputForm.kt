package com.dzdexon.memomartian.ui.screens.edit

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.shared.component.NemoButton
import com.dzdexon.memomartian.ui.shared.component.NemoCarouselComp
import com.dzdexon.memomartian.ui.shared.component.NemoMinimalTextField
import com.dzdexon.memomartian.ui.shared.component.TagManageBottomSheet
import com.dzdexon.memomartian.ui.shared.component.TagView
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.utils.UriPermissionHandler

@Composable
fun NoteInputForm(
    note: Note,
    selectedTags: List<Tag>,
    viewModelEdit: EditScreenViewModel,
    modifier: Modifier = Modifier,
    tagList: List<Tag>,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    createNewTag: (String) -> Unit,
) {
    Log.d("NEMO: NoteInputForm", "NoteInputForm State Rebuild")
    val context = LocalContext.current
    val colors = LocalCustomColors.current
// In this example, the app lets the user select up to 5 media files.
    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            Log.d("NEMO:MEDIA", "Picked uris : $uris")
            if (uris.isNotEmpty()) {
                uris.forEach { uri ->
                    UriPermissionHandler.getUriPermission(context, uri)
                }
                var imageString: String? = null
                uris.forEach { str ->
                    imageString = if (imageString != null) {
                        "$imageString,$str"
                    } else {
                        str.toString()
                    }

                    Log.d("NEMO:Image URI", imageString.toString())

                }
                val oldString = note.imageUri
                val newString = if (oldString != null) {
                    "$oldString,$imageString"
                } else {
                    imageString
                }
                viewModelEdit.updateUI(
                    image = newString, updateIt = EditScreenViewModel.UpdateIt.IMAGE
                )
                Log.d("Note UI Image ", note.imageUri.toString())

            }
        }

    var showSheet by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(showSheet) {
        Log.d("NEMO: showSheet Variable :", "$showSheet")
    }
    if (showSheet) {
        TagManageBottomSheet(
            addTagToNote = addTagToNote,
            removeTagFromNote = removeTagFromNote,
            selectedTags = selectedTags,
            tagList = tagList,
            createNewTag = createNewTag,
        ) {
            showSheet = false
        }
    }
    Surface(
        modifier = modifier, color = colors.primary, contentColor = colors.onPrimary
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .padding(horizontal = 4.dp),
            ) {
                key(note.imageUri) {
                    NemoCarouselComp(
                        imageList = note.imageUri?.split(",") ?: listOf(),
                        height = 250.dp,
                        shadowColor = colors.primary
                    )
                }
                Spacer(Modifier.height(8.dp))
                TagView(
                    selectedTags.map { it.tagName }.toList()
                )
                NemoMinimalTextField(
                    value = note.title,
                    onValueChange = {
                        viewModelEdit.updateUI(
                            title = it, updateIt = EditScreenViewModel.UpdateIt.TITLE
                        )
                    },
                    placeholderText = "Title",
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    textStyle = MaterialTheme.typography.titleLarge
                )
                NemoMinimalTextField(
                    value = note.content,
                    onValueChange = {
                        viewModelEdit.updateUI(
                            content = it, updateIt = EditScreenViewModel.UpdateIt.CONTENT
                        )
                    },
                    placeholderText = "Content",
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                NemoButton(onClick = {
                    showSheet = true
                }) {
                    Icon(
                        painter = painterResource(R.drawable.hash),
                        contentDescription = "Pick Images Icon",
                        modifier = Modifier.size(24.dp)
                    )

                }
                Spacer(Modifier.width(8.dp))
                NemoButton(onClick = {
                    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Icon(
                        painter = painterResource(R.drawable.images_square),
                        contentDescription = "Pick Images Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}


