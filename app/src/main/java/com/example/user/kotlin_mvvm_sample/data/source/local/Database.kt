package com.example.user.kotlin_mvvm_sample.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.user.kotlin_mvvm_sample.data.model.Post


@Database(entities = arrayOf(Post::class), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}