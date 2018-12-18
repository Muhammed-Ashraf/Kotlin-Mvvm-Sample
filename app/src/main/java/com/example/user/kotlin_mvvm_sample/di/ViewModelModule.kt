package com.example.user.kotlin_mvvm_sample.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.user.kotlin_mvvm_sample.di.util.ViewModelKey
import com.example.user.kotlin_mvvm_sample.ui.main.PostsViewModel
import com.example.user.kotlin_mvvm_sample.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    internal abstract fun bindPostsViewModel(postsViewModel: PostsViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}