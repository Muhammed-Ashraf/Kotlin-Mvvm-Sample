package com.example.user.kotlin_mvvm_sample.data.remote

import com.example.user.kotlin_mvvm_sample.data.local.Cryptocurrency
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("ticker/")
    fun getCryptocurrencies(@Query("start") start: String): Observable<List<Cryptocurrency>>
}