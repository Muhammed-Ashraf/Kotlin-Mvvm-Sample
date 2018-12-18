package com.example.user.kotlin_mvvm_sample.di.module

import com.example.user.kotlin_mvvm_sample.ui.apicall.PostsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostsActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributePostsListFragment(): PostsListFragment

}
