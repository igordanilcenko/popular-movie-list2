package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihardanilchanka.sampleapp2.data.model.ReviewDto

@Entity(tableName = "review")
data class ReviewEntity(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @PrimaryKey
    val id: String,
    val author: String,
    val content: String,
    @ColumnInfo(name = "sort_order")
    val sortOrder: Int
) {

    fun toDto() = ReviewDto(id = id, author = author, content = content)
}
