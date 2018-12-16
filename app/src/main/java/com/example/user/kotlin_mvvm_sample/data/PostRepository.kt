package com.example.user.kotlin_mvvm_sample.data

import android.util.Log
import com.example.user.kotlin_mvvm_sample.data.source.local.PostsDao
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.data.source.remote.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class PostRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val postsDao: PostsDao
) {

//    fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
//        val observableFromApi = getCryptocurrenciesFromApi()
//        val observableFromDb = getCryptocurrenciesFromDb()
//        return Observable.concatArrayEager(observableFromApi, observableFromDb)
//    }

    fun getPosts(): Observable<List<Post>> {

        val observableFromApi = getPostsFromApi();
        val observableFromDb = getPostsFromDb()
        return Observable.concatArrayEager(observableFromApi, observableFromDb);
    }

    private fun getPostsFromApi(): Observable<List<Post>> {
        return apiInterface.getPosts().doOnNext {
            Log.e("REPOSITORY API * ", it.size.toString())
            for (item in it) {
                postsDao.insertPost(item)
            }
        }
    }

//    fun getCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
//        return apiInterface.getCryptocurrencies("0")
//            .doOnNext {
//                Log.e("REPOSITORY API * ", it.size.toString())
//                for (item in it) {
//                    postsDao.insertCryptocurrency(item)
//                }
//            }
//    }

    fun getPostsFromDb(): Observable<List<Post>> {
        return postsDao.queryPosts().toObservable().doOnNext {
            Log.i("size", it.size.toString())
        }
    }
}
