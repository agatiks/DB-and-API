package com.example.lab8_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase(){
    abstract fun dao(): Dao
}