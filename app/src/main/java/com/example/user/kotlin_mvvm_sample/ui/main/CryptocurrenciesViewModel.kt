package com.example.user.kotlin_mvvm_sample.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.user.kotlin_mvvm_sample.data.CryptocurrencyRepository
import com.example.user.kotlin_mvvm_sample.data.model.Cryptocurrency
import com.example.user.kotlin_mvvm_sample.data.model.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CryptocurrenciesViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

    var postsResult: MutableLiveData<List<Post>> = MutableLiveData()
    var postsError: MutableLiveData<String> = MutableLiveData()


    fun postsResult(): LiveData<List<Post>> {
        return postsResult
    }

    fun postsError(): LiveData<String> {
        return postsError
    }

    fun loadPosts() {
        compositeDisposable.add(
            cryptocurrencyRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ postsList ->
                    if (postsList.isEmpty()) return@subscribe
                    else postsResult.postValue(postsList)

                }, { err -> postsError.postValue(err.message) })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}