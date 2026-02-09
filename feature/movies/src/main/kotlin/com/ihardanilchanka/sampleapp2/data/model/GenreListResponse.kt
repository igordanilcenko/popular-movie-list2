package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class GenreListResponse(
    @param:Json(name = "genres") val genres: List<GenreDto>
)
