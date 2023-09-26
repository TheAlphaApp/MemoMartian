package com.dzdexon.memomartian.ui.shared.component

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.dzdexon.memomartian.NotesApplication
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputForm(
    note: Note,
    isNoteValid: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (Note) -> Unit = {},
    onSaveClick: () -> Unit,
    tagList: List<Tag>,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    createNewTag: (String) -> Boolean,
) {
    var showTagDialog by rememberSaveable {
        mutableStateOf(false)
    }


// In this example, the app lets the user select up to 5 media files.
    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->

            if (uris.isNotEmpty()) {
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

                    Log.d("Image URI", imageString.toString())

                }
                val oldString = note.imageUri
                val newString = if (oldString != null) {
                    "$oldString,$imageString"
                } else {
                    imageString
                }
                onValueChange(
                    note.copy(imageUri = newString)
                )
                Log.d("Note UI Image ", note.imageUri.toString())

            }
        }

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        TagManageBottomSheet(
            addTagToNote = addTagToNote,
            removeTagFromNote = removeTagFromNote,
            selectedTags = note.tags,
            tagList = tagList,
            createNewTag = createNewTag,
        ) {
            showSheet = false
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        if (!note.imageUri.isNullOrEmpty()) {
            val imageList = note.imageUri.split(",").toTypedArray()
            BuildImageSlider(imageList.toList())
//            imageList.forEach { imageStr ->
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        ImageRequest
//                            .Builder(LocalContext.current)
//                            .data(data = Uri.parse(imageStr))
//                            .build()
//                    ),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(300.dp)
//                        .padding(6.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }

        }

        TextField(
            value = note.title,
            onValueChange = { onValueChange(note.copy(title = it)) },
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
            value = note.content,
            onValueChange = { onValueChange(note.copy(content = it)) },
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
                note.tags.contains(tag.id)
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

        if (isNoteValid) Button(
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
                            selected = note.tags.contains(tag.id),
                            onClick = {
                                if (note.tags.contains(tag.id)) {
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
                selected = false,
                enabled = true,
                onClick = {

                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BuildImageSlider(sliderList: List<String> = listOf()) {

    val pagerState = rememberPagerState(
        initialPage = 1,
        initialPageOffsetFraction = 0f
    ) {
        // provide pageCount
        sliderList.size
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.height(300.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        pageSpacing = 8.dp,
        pageSize = PageSize.Fill
    ) { page ->

        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .graphicsLayer {

                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
//                    alpha = lerp(
//                        start = 0.70f,
//                        stop = 1f,
//                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                    )
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(sliderList[page])
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .offset {
                        // Calculate the offset for the current page from the
                        // scroll position
                        val pageOffset =
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        // Then use it as a multiplier to apply an offset
                        IntOffset(
                            x = (70.dp * pageOffset).roundToPx(),
                            y = 0,
                        )
                    }
            )
        }

    }
}