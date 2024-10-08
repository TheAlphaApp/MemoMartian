package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.utils.HelperFunctions

@Composable
fun NoteCard(
    note: Note,
    tagsList: List<Tag>,
    onClick: (Long) -> Unit
) {
    val colors = LocalCustomColors.current
    Card(
        colors = CardColors(
            containerColor = colors.secondary,
            contentColor = colors.onSecondary,
            disabledContainerColor = colors.tertiary,
            disabledContentColor = colors.onTertiary
        ),
        modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .clickable {
            onClick(note.noteId)
        }) {

        Column(Modifier.padding(top = 8.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)) {
            Text(
                text = note.title,
                maxLines = 1,
                color = colors.onPrimary,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = note.content,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )

            tagsList.forEach {
                Text(text = "#${it.tagName}")
            }
            Text(
                text = HelperFunctions.formatOffsetDateTime(note.lastUpdate) ?: "",

                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

