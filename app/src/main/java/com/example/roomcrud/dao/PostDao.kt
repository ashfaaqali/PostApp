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

    @Query("UPDATE posts_table SET upvotes = upvotes + 1 WHERE id = :postId")
    suspend fun upvotePost(postId: Int)

    @Query("UPDATE posts_table SET downvotes = downvotes + 1 WHERE id = :postId")
    suspend fun downvotePost(postId: Int)
}
