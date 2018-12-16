package com.example.user.kotlin_mvvm_sample.data.model

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import java.io.Serializable

//class PostModel : Parcelable {
//
//    @SerializedName("userId")
//    @Expose
//    var userId: Int? = null
//    @SerializedName("id")
//    @Expose
//    var id: Int? = null
//    @SerializedName("title")
//    @Expose
//    var title: String? = null
//    @SerializedName("body")
//    @Expose
//    var body: String? = null
//
//    protected constructor(`in`: Parcel) {
//        this.userId = `in`.readValue(Int::class.java.classLoader) as Int
//        this.id = `in`.readValue(Int::class.java.classLoader) as Int
//        this.title = `in`.readValue(String::class.java.classLoader) as String
//        this.body = `in`.readValue(String::class.java.classLoader) as String
//    }
//
//    constructor() {}
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        dest.writeValue(userId)
//        dest.writeValue(id)
//        dest.writeValue(title)
//        dest.writeValue(body)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object {
//        @JvmField
//        val CREATOR: Parcelable.Creator<PostModel> = object : Creator<PostModel> {
//
//
//            override fun createFromParcel(`in`: Parcel): PostModel {
//                return PostModel(`in`)
//            }
//
//            override fun newArray(size: Int): Array<PostModel?> {
//                return arrayOfNulls(size)
//            }
//
//        }
//    }
//
//}

@Entity(
    tableName = "posts"
)

data class Post(

    @Json(name = "userId")
    val userId: String,

    @Json(name = "id")
    val id: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "body")
    val body: String


) : Serializable