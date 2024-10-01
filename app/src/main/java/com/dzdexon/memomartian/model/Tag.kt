package com.dzdexon.memomartian.model

import com.dzdexon.memomartian.data.entities.TagEntity

data class Tag(
    val tagId: Long = 0,
    val tagName: String,
)

fun Tag.asEntity(): TagEntity {
    return TagEntity(
        tagId = tagId,
        tagName = tagName,
    )
}
