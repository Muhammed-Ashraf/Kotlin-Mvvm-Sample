package com.example.user.kotlin_mvvm_sample.data.source.remote

import com.example.user.kotlin_mvvm_sample.data.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
}