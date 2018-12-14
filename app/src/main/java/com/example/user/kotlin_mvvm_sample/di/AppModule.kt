package com.example.user.kotlin_mvvm_sample.di

import android.app.Application

import android.arch.persistence.room.Room
import com.example.user.kotlin_mvvm_sample.BuildConfig
import com.example.user.kotlin_mvvm_sample.data.source.local.CryptocurrenciesDao
import com.example.user.kotlin_mvvm_sample.data.source.local.Database
import com.example.user.kotlin_mvvm_sample.data.source.remote.ApiInterface
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

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideApplication(application: Application): Application = application

    @Provides
    @Singleton
    fun provideCryptocurrenciesDatabase(app: Application): Database = Room.databaseBuilder(
        app,
        Database::class.java, "cryptocurrencies_db"
    ).build()

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao = database.cryptocurrenciesDao()

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}