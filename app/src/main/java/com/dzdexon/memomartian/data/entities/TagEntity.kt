package com.dzdexon.memomartian.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dzdexon.memomartian.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Long = 0,
    val tagName: String,
)

fun TagEntity.asExternalModel(): Tag {
    return Tag(
        tagId = this.tagId,
        tagName = this.tagName,
    )
}