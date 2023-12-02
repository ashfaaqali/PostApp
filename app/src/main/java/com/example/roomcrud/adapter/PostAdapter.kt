package com.example.roomcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.roomcrud.R
import com.example.roomcrud.model.Post
import androidx.recyclerview.widget.RecyclerView
import com.example.roomcrud.databinding.PostCardBinding
import com.example.roomcrud.fragments.AllPostsDirections
import com.example.roomcrud.viewmodel.PostViewModel

class PostAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    private var postList = emptyList<Post>()
    private var mPostViewModel = ViewModelProvider(viewModelStoreOwner)[PostViewModel::class.java]
    inner class MyViewHolder(val binding: PostCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = postList[position]

        with(holder.binding) {
            postTitle.text = currentItem.postTitle
            postDesc.text = currentItem.postDescription
            upvoteCount.text = currentItem.upvotes.toString()
            downvoteCount.text = currentItem.downvotes.toString()

            readMoreBtn.setOnClickListener{
                val action = AllPostsDirections.actionAllPostsToViewPost(currentItem)
                it.findNavController().navigate(action)
            }

            upvoteIcon.setOnClickListener{
                currentItem.upvotes++
                upvoteCount.text = currentItem.upvotes.toString()
                mPostViewModel.updatePost(currentItem)
            }

            downvoteIcon.setOnClickListener{
                currentItem.downvotes++
                downvoteCount.text = currentItem.downvotes.toString()
                mPostViewModel.updatePost(currentItem)
            }
        }
    }

    fun setData(post: List<Post>){
        this.postList = post
        notifyDataSetChanged()
    }
}
