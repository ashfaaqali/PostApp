package com.example.roomcrud.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val postTitle: String,
    val postDescription: String,
    val authorName: String,
    val creationDate: String,
    var upvotes: Int = 0,
    var downvotes: Int = 0
): Parcelable
