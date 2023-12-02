package com.example.roomcrud.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomcrud.R
import com.example.roomcrud.databinding.DeleteAlertBinding
import com.example.roomcrud.databinding.FragmentViewPostBinding
import com.example.roomcrud.viewmodel.PostViewModel

class ViewPost : Fragment() {

    private lateinit var binding: FragmentViewPostBinding
    private val args by navArgs<ViewPostArgs>()
    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPostBinding.inflate(layoutInflater)
        mPostViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        setSafeArgsDataToViews()

        binding.viewPostEditBtn.setOnClickListener{
            editPostDetails()
        }

        binding.viewPostDeleteBtn.setOnClickListener {
            deletePost()
        }

        initUi()

        return binding.root
    }

    private fun editPostDetails() {
        val currentItem = args.currentPost
        val action = ViewPostDirections.actionViewPostToUpdatePost(currentItem)
        findNavController().navigate(action)
    }

    private fun deletePost(){
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(requireContext())

        val view = DeleteAlertBinding.inflate(layoutInflater)
        builder.setView(view.root)

        view.cancelBtn.setOnClickListener{
            alertDialog?.dismiss()
        }

        view.yesBtn.setOnClickListener{
            alertDialog?.dismiss()
            mPostViewModel.deletePost(args.currentPost)
            Toast.makeText(requireContext(), "Successfully delete ${args.currentPost.postTitle}", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun initUi() {
        binding.customActionBar.actionBarTitle.text = "View Post"
        binding.customActionBar.homeBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun setSafeArgsDataToViews() {
        binding.viewPostTitle.text = args.currentPost.postTitle
        binding.viewPostDesc.text = args.currentPost.postDescription
        binding.viewPostAuthor.text = args.currentPost.authorName
        binding.viewPostDate.text = args.currentPost.creationDate
        binding.viewPostUpvoteCount.text = args.currentPost.upvotes.toString()
        binding.viewPostDownvoteCount.text = args.currentPost.downvotes.toString()
    }

}