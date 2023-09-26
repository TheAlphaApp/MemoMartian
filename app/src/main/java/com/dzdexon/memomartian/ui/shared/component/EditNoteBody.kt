package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag

@Composable
fun EditNoteBody(
    note: Note,
    tagList: List<Tag>,
    isNoteValid: Boolean,
    onNoteValueChange: (Note) -> Unit,
    onSaveClick: () -> Unit,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    createNewTag: (String) -> Boolean,
    modifier: Modifier = Modifier,
) {

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NoteInputForm(
                note = note,
                onValueChange = onNoteValueChange,
                tagList = tagList,
                addTagToNote = addTagToNote,
                removeTagFromNote = removeTagFromNote,
                onSaveClick = onSaveClick,
                isNoteValid = isNoteValid,
                createNewTag = createNewTag,
            )

        }
    }

}
