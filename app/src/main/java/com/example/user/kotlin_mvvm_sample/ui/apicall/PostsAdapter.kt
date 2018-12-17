package com.example.user.kotlin_mvvm_sample.ui.apicall

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.kotlin_mvvm_sample.R
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.databinding.ItemPostBinding

class PostsAdapter(private val postClickCallback: PostClickCallback?) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    internal var postList: List<Post>? = null

    fun setPostList(postList: List<Post>) {
        if (this.postList == null) {
            this.postList = postList
            notifyItemRangeInserted(0, postList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@PostsAdapter.postList!!.size
                }

                override fun getNewListSize(): Int {
                    return postList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@PostsAdapter.postList!![oldItemPosition].id === postList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = postList[newItemPosition]
                    val old = postList[oldItemPosition]
                    return project.id === old.id
                }
            })
            this.postList = postList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: ItemPostBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.item_post,
                parent, false
            )

        binding.callback = postClickCallback

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.post = postList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (postList == null) 0 else postList!!.size
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)
}