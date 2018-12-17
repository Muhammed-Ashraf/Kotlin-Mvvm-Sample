package com.example.user.kotlin_mvvm_sample.di

import com.example.user.kotlin_mvvm_sample.ui.main.PostsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(PostsActivityModule::class)])
    abstract fun bindMainActivity(): PostsActivity

}