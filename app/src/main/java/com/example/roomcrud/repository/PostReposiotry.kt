package com.example.roomcrud.repository

import androidx.lifecycle.LiveData
import com.example.roomcrud.dao.PostDao
import com.example.roomcrud.model.Post

class PostRepository(private val postDao: PostDao) {
    val getAllPosts: LiveData<List<Post>> = postDao.getAllPosts()

    suspend fun createPost(post: Post){
        postDao.createPost(post)
    }

    suspend fun updatePost(post: Post){
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post){
        postDao.deletePost(post)
    }

    suspend fun deleteAllPosts(){
        postDao.deleteAllPosts()
    }

    suspend fun upvotePost(post: Post) {
        postDao.upvotePost(post)
    }

    suspend fun downvotePost(post: Post) {
        postDao.downvotePost(post)
    }
}
