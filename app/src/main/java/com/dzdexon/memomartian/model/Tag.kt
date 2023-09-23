package com.dzdexon.memomartian.model

import com.dzdexon.memomartian.data.entities.TagEntity

data class Tag(
    val id: Int = 0,
    val tagName: String,
)

fun Tag.asEntity() = TagEntity(
    id = id,
    tagName = tagName,
)
