package com.dzdexon.memomartian.ui.screens.edit

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.shared.component.NemoButton

import com.dzdexon.memomartian.ui.shared.component.TagManageBottomSheet
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.utils.UriPermissionHandler

@Composable
fun NoteInputForm(
    note: Note,
    selectedTags: List<Tag>,
    viewModelEdit: EditScreenViewModel,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
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
                    image = newString,
                    updateIt = EditScreenViewModel.UpdateIt.IMAGE
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
        modifier = modifier,
        color = colors.primary,
        contentColor = colors.onPrimary
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
                            title = it,
                            updateIt = EditScreenViewModel.UpdateIt.TITLE
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
                            content = it,
                            updateIt = EditScreenViewModel.UpdateIt.CONTENT
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
                NemoButton(
                    onClick = {
                        showSheet = true
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.hash),
                        contentDescription = "Pick Images Icon",
                        modifier = Modifier.size(24.dp)
                    )

                }
                Spacer(Modifier.width(8.dp))
                NemoButton(
                    onClick = {
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


@Composable
fun TagView(tagsList: List<String>) {
    val colors = LocalCustomColors.current
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        tagsList.forEach { tag ->
            Text(
                text = "#$tag",
                fontSize = 18.sp,
                color = colors.onTertiary,
                fontFamily = FontFamily(Font(R.font.ibm_plex_mono_regular))
            )

        }
    }
}


@Composable
fun NemoMinimalTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    textStyle: TextStyle,
    placeholderText: String,
    maxLines: Int = Int.MAX_VALUE
) {
    val colors = LocalCustomColors.current
    TextField(
        value = value,
        onValueChange = { it ->
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = { Text(placeholderText, style = textStyle) },
        modifier = modifier,
        minLines = 1,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            cursorColor = colors.onSecondary,
            focusedPlaceholderColor = colors.onTertiary,
            unfocusedPlaceholderColor = colors.onTertiary,
            focusedTextColor = colors.onPrimary,
            unfocusedTextColor = colors.onPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NemoCarouselComp(
    modifier: Modifier = Modifier,
    imageList: List<String>,
    height: Dp,
    shadowColor: Color,
    isShadowUpsideDown: Boolean = false
) {
    val carouselState = rememberCarouselState { imageList.size }

    if (imageList.isNotEmpty())
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 300.dp,
            modifier = modifier,
            itemSpacing = 1.dp,
            flingBehavior = CarouselDefaults.multiBrowseFlingBehavior(state = carouselState),
        ) { page ->

            Box(
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize(),
                    model = imageList[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = if (isShadowUpsideDown) listOf(
                                    shadowColor.copy(alpha = 1f),
                                    Color.Transparent,
                                ) else listOf(
                                    Color.Transparent,
                                    shadowColor.copy(alpha = 1f)
                                )
                            )
                        )
                )
            }

        }


}