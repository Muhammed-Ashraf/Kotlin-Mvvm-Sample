package com.example.user.kotlin_mvvm_sample

import android.app.Activity
import android.app.Application
import com.example.user.kotlin_mvvm_sample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KotlinMvvmSampleApplication : Application(), HasActivityInjector {
    @Inject
    lateinit internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().application(this).build().inject(this)
//        DaggerAppComponent.builder().build().inject(this)

//        DaggerAppComponent.builder()
//            .appModule(AppModule(this))
//            .netModule(NetModule(BuildConfig.URL))
//            .build().inject(this)
    }

    override fun activityInjector() = activityDispatchingAndroidInjector
}
