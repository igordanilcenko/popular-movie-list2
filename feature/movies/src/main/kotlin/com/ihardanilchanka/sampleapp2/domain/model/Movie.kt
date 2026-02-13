package com.ihardanilchanka.sampleapp2.domain.model

import java.util.Date

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Date?,
    val voteAverage: Double,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genreNames: List<String>,
)