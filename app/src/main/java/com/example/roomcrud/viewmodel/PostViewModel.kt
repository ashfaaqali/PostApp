package com.example.roomcrud.viewmodel

import android.app.Application
import android.service.autofill.UserData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcrud.database.AppDatabase
import com.example.roomcrud.model.Post
import com.example.roomcrud.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {
    val getAllPosts: LiveData<List<Post>>
    private val repository: PostRepository

    init {
        val postDao = AppDatabase.getDatabase(application).postDao()
        repository = PostRepository(postDao)
        getAllPosts = repository.getAllPosts
    }

    fun createPost(post: Post){
        viewModelScope.launch(Dispatchers.IO){
            repository.createPost(post)
        }
    }

    fun updatePost(post: Post){
        viewModelScope.launch(Dispatchers.IO){
            repository.updatePost(post)
        }
    }

    fun deletePost(post: Post){
        viewModelScope.launch(Dispatchers.IO){
            repository.deletePost(post)
        }
    }

    fun deleteAllPosts(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllPosts()
        }
    }

    fun upvotePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upvotePost(postId)
        }
    }

    fun downvotePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.downvotePost(postId)
        }
    }
}

