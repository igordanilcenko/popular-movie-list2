package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.ReviewEntity
import com.ihardanilchanka.sampleapp2.domain.model.Review

data class ReviewDto(
    val id: String,
    val author: String,
    val content: String
) {

    fun toModel() = Review(
        id = id,
        author = author,
        content = content,
    )

    fun toEntity(movieId: Int, index: Int) = ReviewEntity(
        movieId = movieId,
        id = id,
        author = author,
        content = content,
        sortOrder = index
    )
}