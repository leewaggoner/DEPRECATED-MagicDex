package com.wreckingball.magicdex.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?
)