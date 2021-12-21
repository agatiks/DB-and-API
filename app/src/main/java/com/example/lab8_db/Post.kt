package com.example.lab8_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post (
    @PrimaryKey(autoGenerate = true) val id: Int,
    //@Json(name = "id") val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val body: String)
