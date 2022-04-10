package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class ReviewListResponse(
    val id: Int,
    @Json(name = "results") val reviews: List<ReviewDto>
)