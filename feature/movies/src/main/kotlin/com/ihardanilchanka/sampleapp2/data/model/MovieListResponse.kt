package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class MovieListResponse(
    @param:Json(name = "page") val page: Int,
    @param:Json(name = "results") val movies: List<MovieDto>,
    @param:Json(name = "total_results") val totalResults: Int,
    @param:Json(name = "total_pages") val totalPages: Int,
)