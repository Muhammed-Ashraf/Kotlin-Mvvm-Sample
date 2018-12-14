package com.example.user.kotlin_mvvm_sample.di

import android.app.Application

import android.arch.persistence.room.Room
import com.example.user.kotlin_mvvm_sample.local.CryptocurrenciesDao
import com.example.user.kotlin_mvvm_sample.local.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideApplication(application: Application): Application = application

    @Provides
    @Singleton
    fun provideCryptocurrenciesDatabase(app: Application): Database = Room.databaseBuilder(app,
        Database::class.java, "cryptocurrencies_db").build()

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao = database.cryptocurrenciesDao()



}