package com.example.user.kotlin_mvvm_sample.di

import android.app.Application
import com.example.user.kotlin_mvvm_sample.KotlinMvvmSampleApplication
import com.example.user.kotlin_mvvm_sample.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton

//@Singleton
//@Component(
//    modules = arrayOf(AndroidInjectionModule::class, ActivityBuilder::class, AppModule::class)
//)
//interface AppComponent {
//  fun inject(app: KotlinMvvmSampleApplication)
//}

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)]) //TODO
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: KotlinMvvmSampleApplication)
}