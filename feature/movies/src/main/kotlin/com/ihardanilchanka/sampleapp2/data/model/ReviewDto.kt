package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.ReviewEntity
import com.ihardanilchanka.sampleapp2.domain.model.Review
import com.squareup.moshi.Json

data class ReviewDto(
    @param:Json(name = "id") val id: String,
    @param:Json(name = "author") val author: String,
    @param:Json(name = "content") val content: String
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
