package com.example.roomcrud.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomcrud.model.Post


@Dao
interface PostDao {
    @Query("SELECT * FROM posts_table ORDER BY upvotes DESC, downvotes ASC, id ASC")
    fun getAllPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM posts_table")
    suspend fun deleteAllPosts()

    @Update
    suspend fun upvotePost(post: Post)

    @Update
    suspend fun downvotePost(post: Post)
}
