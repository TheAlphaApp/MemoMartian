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
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.data.entities.TagEntity

@Composable
fun EditNoteBody(
    noteUiState: NoteUiState,
    tagEntityList: List<TagEntity>,
    onNoteValueChange: (NoteUiState) -> Unit,
    onSaveClick: () -> Unit,
    addTagToNote: (TagEntity) -> Unit,
    removeTagFromNote: (TagEntity) -> Unit,
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
                noteUiState = noteUiState,
                onValueChange = onNoteValueChange,
                tagEntityList = tagEntityList,
                addTagToNote = addTagToNote,
                removeTagFromNote = removeTagFromNote,
                onSaveClick = onSaveClick
            )

        }
    }

}
