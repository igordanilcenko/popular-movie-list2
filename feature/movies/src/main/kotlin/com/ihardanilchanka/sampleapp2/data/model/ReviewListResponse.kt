package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class ReviewListResponse(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "results") val reviews: List<ReviewDto>
)
