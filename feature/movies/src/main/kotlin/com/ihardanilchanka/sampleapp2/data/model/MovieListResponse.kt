package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import com.squareup.moshi.Json

data class MovieListResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val movies: List<MovieDto>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)