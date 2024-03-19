package com.example.justfortoday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "jft", indices = [Index(value = ["month", "day"], unique = true)])
data class Entry(
    @PrimaryKey
    val id: Int?,
    val month: Int?,
    val day: Int?,
    val title: String?,
    val excerpt: String?,
    val source: String?,
    val content: String?,
    val summary: String?
)