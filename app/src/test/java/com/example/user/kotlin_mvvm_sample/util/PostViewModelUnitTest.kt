package com.example.user.kotlin_mvvm_sample.util

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.view.View
import com.example.user.kotlin_mvvm_sample.data.PostRepository
import com.example.user.kotlin_mvvm_sample.data.model.Post
import com.example.user.kotlin_mvvm_sample.ui.main.PostsViewModel
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.reset


class PostViewModelUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val postRepository = mock<PostRepository>()

    val compositeDisposable = mock<CompositeDisposable>()

    val observerState = mock<Observer<List<Post>>>()

    val postsViewModel by lazy {
        PostsViewModel(
            compositeDisposable,
            postRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }


    @Before
    fun initTest() {
        reset(postRepository, observerState)
    }


    @Test
    fun testPostViewModel_getPosts() {
        val response = arrayListOf(cryptoPOJOmodel())

        whenever(this.postRepository.getPosts())
            .thenReturn(Observable.just(response))

        postRepository.getPosts()
            .test()
            .assertComplete()
    }

//    @Test
//    fun testCryptoListUseCases_getCryptoList_response() {
//        val response = arrayListOf(cryptoPOJOmodel())
//        whenever(coinMarketCapRepository.getCryptoList(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//            .thenReturn(Single.just(response))
//
//        val expectedList = arrayListOf(cryptoViewModelFrom(cryptoPOJOmodel()))
//
//        cryptoListUseCases.getCryptoListBy(0)
//            .test()
//            .assertValue(expectedList)
//    }

    @Test
    fun testPostViewModel_getPosts_Error() {
        val response = Throwable("Error response")
        whenever(this.postRepository.getPosts())
            .thenReturn(Observable.error(response))

        postRepository.getPosts()
            .test()
            .assertError(response)

    }

    @Test
    fun testPostViewModel_getPosts_response() {
        val response = arrayListOf(cryptoPOJOmodel())

        whenever(this.postRepository.getPosts())
            .thenReturn(Observable.just(response))

        val expectedList = arrayListOf(cryptoPOJOmodel())

        postRepository.getPosts()
            .test()
            .assertValue(expectedList)
    }

    @Test
    fun testPostViewModel_getPosts_Completed() {

////        whenever(this.postRepository.getPosts()).thenAnswer {
////            return@thenAnswer Observable.just(ArgumentMatchers.anyList<Post>())
////        }
//        whenever(this.postRepository.getPosts())
//            .thenReturn(Observable.just(emptyList()))
//
////        this.postRepository.getPosts()
////            .test()
////            .assertComplete()
//
//        this.postRepository.getPosts()
//            .test()
//            .assertResult(emptyList())


        val response = arrayListOf(cryptoPOJOmodel())

        whenever(this.postRepository.getPosts())
            .thenReturn(Observable.just(response))

        postsViewModel.postsResult.observeForever(observerState)

        postsViewModel.loadPosts()
        Mockito.verify(postRepository).getPosts()

        val expectedLoadingVisibility = View.GONE
        val expectedTotalTextVisibility = View.VISIBLE
//        Mockito.verify(observerState, Mockito.times(1)).onChanged()
        assertEquals(expectedLoadingVisibility, postsViewModel.loadingVisibility.value)
        assertEquals(expectedTotalTextVisibility, postsViewModel.totalTextVisibility.value)
        assertEquals(null, postsViewModel.errorMessage.value)
        assertEquals("total: ${postsViewModel.postsResult.value!!.size} posts", postsViewModel.totalItemsMessage.value)


    }

//    @Test
//    fun testPostViewModel_getPosts_Error() {
//        val response = Throwable("Error response")
//        whenever(this.postRepository.getPosts())
//            .thenReturn(Observable.just(emptyList()))
//            .thenReturn(Observable.error(response))
//
//        this.postRepository.getPosts()
//            .test()
//            .assertError(response)
//    }

}