package com.example.roomcrud.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomcrud.R
import com.example.roomcrud.adapter.PostAdapter
import com.example.roomcrud.databinding.DeleteAlertBinding
import com.example.roomcrud.databinding.FragmentAllPostsBinding
import com.example.roomcrud.viewmodel.PostViewModel

class AllPosts : Fragment() {

    private lateinit var binding: FragmentAllPostsBinding
    private lateinit var mPostViewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllPostsBinding.inflate(layoutInflater)
        adapter = PostAdapter(this)
        initUi()
        // Attach PostAdapter to the Recycler View
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPostViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        mPostViewModel.getAllPosts.observe(viewLifecycleOwner, Observer { post ->
            adapter.setData(post)
            updateDeleteAllBtnVisibility(post.isEmpty())
        })

        binding.createPostFab.setOnClickListener {
            findNavController().navigate(R.id.action_allPosts_to_createPost)
        }

        binding.customActionBar.deleteAllBtn.setOnClickListener {
            deleteAllPosts()
        }

        return binding.root
    }

    private fun updateDeleteAllBtnVisibility(isEmpty: Boolean) {
        if (!isEmpty) {
            binding.customActionBar.deleteAllBtn.visibility = View.VISIBLE
        } else {
            binding.customActionBar.deleteAllBtn.visibility = View.GONE
        }
    }

    private fun deleteAllPosts() {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(requireContext())

        val view = DeleteAlertBinding.inflate(layoutInflater)
        builder.setView(view.root)

        view.tv2.text = "Are you sure you want to\ndelete all posts?"

        view.cancelBtn.setOnClickListener {
            alertDialog?.dismiss()
        }

        view.yesBtn.setOnClickListener {
            alertDialog?.dismiss()
            mPostViewModel.deleteAllPosts()
            Toast.makeText(requireContext(), "Successfully deleted all posts!", Toast.LENGTH_SHORT)
                .show()
        }

        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun initUi() {
        binding.customActionBar.actionBarTitle.text = "All Posts"
        binding.customActionBar.homeBtn.visibility = View.GONE
        binding.customActionBar.homeBtn.isClickable = false
    }
}