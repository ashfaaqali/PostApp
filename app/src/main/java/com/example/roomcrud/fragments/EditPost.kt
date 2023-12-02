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
import androidx.navigation.fragment.navArgs
import com.example.roomcrud.R
import com.example.roomcrud.databinding.FragmentEditPostBinding
import com.example.roomcrud.model.Post
import com.example.roomcrud.viewmodel.PostViewModel

class EditPost : Fragment() {
    private lateinit var binding: FragmentEditPostBinding
    private val args by navArgs<EditPostArgs>()
    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPostBinding.inflate(layoutInflater)
        mPostViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        initUi()

        setSafeArgsDataToViews()

        binding.saveChangesBtn.setOnClickListener {
            updateData()
        }

        return binding.root
    }

    private fun updateData() {
        val postTitle = binding.updateTitleTextInput.text.toString()
        val postDescription = binding.updateDescTextInput.text.toString()
        val authorName = binding.updateAuthorTextInput.text.toString()
        val creationDate = args.currentPost.creationDate
        val currentUpVoteCount = args.currentPost.upvotes
        val currentDownVoteCount = args.currentPost.downvotes

        if (inputCheck(postTitle, postDescription, authorName)) {
            //Create Post object
            val updatedData =
                Post(args.currentPost.id, postTitle, postDescription, authorName, creationDate, currentUpVoteCount, currentDownVoteCount)
            // Add data to database
            mPostViewModel.updatePost(updatedData)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            // Navigate to AllPosts fragment
            findNavController().popBackStack(R.id.allPosts, false)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(
        postTitle: String,
        postDescription: String,
        authorName: String
    ): Boolean {
        return !(TextUtils.isEmpty(postTitle) || TextUtils.isEmpty(postDescription) || TextUtils.isEmpty(
            authorName
        ))
    }

    private fun setSafeArgsDataToViews() {
        binding.updateTitleTextInput.setText(args.currentPost.postTitle)
        binding.updateDescTextInput.setText(args.currentPost.postDescription)
        binding.updateAuthorTextInput.setText(args.currentPost.authorName)
    }

    private fun initUi() {
        binding.customActionBar.actionBarTitle.text = "Edit Post"
        binding.customActionBar.homeBtn.setOnClickListener{
            findNavController().popBackStack(R.id.allPosts, false)
        }
    }

}