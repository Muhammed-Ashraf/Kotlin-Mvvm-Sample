package com.example.user.kotlin_mvvm_sample.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider

import android.arch.persistence.room.Room
import android.content.Context
import com.example.user.kotlin_mvvm_sample.BuildConfig
import com.example.user.kotlin_mvvm_sample.data.source.local.PostsDao
import com.example.user.kotlin_mvvm_sample.data.source.local.Database
import com.example.user.kotlin_mvvm_sample.data.source.remote.ApiInterface
import com.example.user.kotlin_mvvm_sample.ui.main.PostsViewModelFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

//    @Provides
//    @Singleton
//    fun provideApplication(): Application = app

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providePostsDatabase(app: Context): Database = Room.databaseBuilder(
        app,
        Database::class.java, "posts_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(database: Database): PostsDao = database.postsDao()

//    @Provides
//    fun providePostsViewModelFactory(
//        factory: PostsViewModelFactory
//    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    internal fun provideApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}