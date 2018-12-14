package com.example.user.kotlin_mvvm_sample.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = arrayOf(Cryptocurrency::class), version = 1)
abstract class Database : RoomDatabase() {
  abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}