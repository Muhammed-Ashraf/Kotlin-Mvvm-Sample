package com.example.user.kotlin_mvvm_sample.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.user.kotlin_mvvm_sample.data.model.Post

import io.reactivex.Single

@Dao
interface PostsDao {

  @Query("SELECT * FROM posts")
  fun queryPosts(): Single<List<Post>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPost(post: Post)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  fun insertAllPosts(posts: List<Post>)
}