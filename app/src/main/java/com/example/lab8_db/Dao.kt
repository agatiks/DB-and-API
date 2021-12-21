package com.example.lab8_db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM post")
    fun getAllPosts(): Flow<List<Post>>
    @Query("DELETE FROM post")
    suspend fun clear()
    @Query("SELECT * FROM post")
    suspend fun allRecords(): List<Post>
    @Insert
    suspend fun insertAll(posts: List<Post>)
    @Delete
    suspend fun deletePost(post: Post)

    suspend fun countRecords(): Int {
        return allRecords().size
    }
}