package com.example.user.kotlin_mvvm_sample.di

import com.example.user.kotlin_mvvm_sample.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): PostsActivity

}