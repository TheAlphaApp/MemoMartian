package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.ui.theme.ibmPlexMono
import com.dzdexon.memomartian.utils.HelperFunctions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteCard(
    note: Note,
    tagsList: List<Tag>,
    onClick: (Long) -> Unit,
    showImages: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colors = LocalCustomColors.current


    Card(
        colors = CardColors(
            containerColor = colors.secondary,
            contentColor = colors.onSecondary,
            disabledContainerColor = colors.tertiary,
            disabledContentColor = colors.onTertiary
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(note.noteId)
            }) {

        Column(Modifier) {
            Column(
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            ) {
                if (note.title.isNotBlank())
                    Text(
                        text = note.title,
                        maxLines = 1,
                        color = colors.onPrimary,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                if (note.content.isNotBlank())

                    Text(
                        text = note.content,
                        maxLines = 6,
                        color = colors.onSecondary,

                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                FlowRow(

                ) {
                    tagsList.forEach {
                        Text(
                            modifier = Modifier.padding(end = 4.dp),
                            text = "#${it.tagName}",
                            fontFamily = ibmPlexMono,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onTertiary

                        )
                    }
                }

                if (note.lastUpdate != null)
                    Text(
                        text = HelperFunctions.formatOffsetDateTime(note.lastUpdate) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onTertiary
                    )
            }
            if (showImages)
                NemoCarouselComp(
                    imageList = note.imageUri?.split(",") ?: listOf(),
                    shadowColor = colors.secondary,
                    isShadowUpsideDown = true,
                    height = 100.dp,
                )
        }
    }

}

