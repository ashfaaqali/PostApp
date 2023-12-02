package com.example.roomcrud.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomcrud.R
import com.example.roomcrud.databinding.FragmentCreatePostBinding
import com.example.roomcrud.model.Post
import com.example.roomcrud.viewmodel.PostViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePost : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding
    private lateinit var mPostViewModel:PostViewModel
    private lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(layoutInflater)
        initUi()

        mPostViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        binding.submitBtn.setOnClickListener{
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun initUi() {
        binding.customActionBar.actionBarTitle.text = "Create Post"
        binding.customActionBar.homeBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun insertDataToDatabase() {
        val postTitle = binding.titleTextInput.text.toString()
        val postDescription = binding.descTextInput.text.toString()
        val authorName = binding.authorTextInput.text.toString()

        if (inputCheck(postTitle, postDescription, authorName)){

            //Create Post object
            val post = Post(0, postTitle, postDescription, authorName, getCreationDate(), 0, 0)

            // Add data to database
            mPostViewModel.createPost(post)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate to All Posts
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCreationDate(): String {
        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
    }

    private fun inputCheck(postTitle: String, postDescription: String, authorName: String): Boolean{
        return !(TextUtils.isEmpty(postTitle) || TextUtils.isEmpty(postDescription) || TextUtils.isEmpty(authorName))
    }
}