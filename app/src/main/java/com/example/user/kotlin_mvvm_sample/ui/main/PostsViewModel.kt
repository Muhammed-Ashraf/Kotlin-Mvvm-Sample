package com.example.user.kotlin_mvvm_sample.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.example.user.kotlin_mvvm_sample.data.PostRepository
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.di.module.SCHEDULER_IO
import com.example.user.kotlin_mvvm_sample.di.module.SCHEDULER_MAIN_THREAD
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import javax.inject.Inject
import javax.inject.Named

class PostsViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val postRepository: PostRepository, @Named(SCHEDULER_IO) val subscribeOnScheduler: Scheduler, @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler
) : ViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val totalTextVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val totalItemsMessage: MutableLiveData<String> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    var postsResult: MutableLiveData<List<Post>> = MutableLiveData()
//    var postsError: MutableLiveData<String> = MutableLiveData()


    fun postsResult(): LiveData<List<Post>> {
        return postsResult
    }

//    fun postsError(): LiveData<String> {
//        return postsError
//    }

    fun loadPosts() {
        compositeDisposable.add(
            postRepository.getPosts()
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe({ postsList ->
                    if (postsList.isEmpty()) return@subscribe
                    else {
//                        totalItemsMessage.postValue("total: ${postsList.size} posts")
//                        totalItemsMessage.value  = "total: ${postsList.size} posts"
//                        postsResult.postValue(postsList)
                        onPostsReceived(postsList)
                    }

                }, { err ->
                    //                    postsError.postValue("Error Fetching Data")
//                    onRetrievePostListError()
//                    totalItemsMessage.postValue("Error Fetching data")
                    onError()
                })
//                .subscribe(this::onPostsReceived, this::onError)
        )
    }

    private fun onPostsReceived(postsList: List<Post>) {
        totalItemsMessage.postValue("total: ${postsList.size} posts")
        totalItemsMessage.value = "total: ${postsList.size} posts"
        postsResult.postValue(postsList)
    }

    private fun onError() {
        onRetrievePostListError()
        totalItemsMessage.postValue("Error Fetching data")
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        totalTextVisibility.value = View.GONE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
        totalTextVisibility.value = View.VISIBLE
    }


    private fun onRetrievePostListError() {
        errorMessage.value = "Error Rerieving Data"
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}