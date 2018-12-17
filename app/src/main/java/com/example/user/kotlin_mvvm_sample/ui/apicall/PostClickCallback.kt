package com.example.user.kotlin_mvvm_sample.ui.apicall

import com.example.user.kotlin_mvvm_sample.data.model.Post

interface PostClickCallback {
    fun onClick(post: Post)
}