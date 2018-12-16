package com.example.user.kotlin_mvvm_sample.data

import android.util.Log
import com.example.user.kotlin_mvvm_sample.data.source.local.CryptocurrenciesDao
import com.example.user.kotlin_mvvm_sample.data.model.Cryptocurrency
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.data.source.remote.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val cryptocurrenciesDao: CryptocurrenciesDao
) {

    fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
        val observableFromApi = getCryptocurrenciesFromApi()
        val observableFromDb = getCryptocurrenciesFromDb()
        return Observable.concatArrayEager(observableFromApi, observableFromDb)
    }

    fun getPosts(): Observable<List<Post>> {

        return getPostsFromApi();
    }

    private fun getPostsFromApi(): Observable<List<Post>> {
        return apiInterface.getPosts()
    }

    fun getCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
        return apiInterface.getCryptocurrencies("0")
            .doOnNext {
                Log.e("REPOSITORY API * ", it.size.toString())
                for (item in it) {
                    cryptocurrenciesDao.insertCryptocurrency(item)
                }
            }
    }

    fun getCryptocurrenciesFromDb(): Observable<List<Cryptocurrency>> {
        return cryptocurrenciesDao.queryCryptocurrencies()
            .toObservable()
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }
}