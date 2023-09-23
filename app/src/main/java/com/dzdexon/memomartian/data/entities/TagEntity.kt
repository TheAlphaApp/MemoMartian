package com.dzdexon.memomartian.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tagName: String,
)