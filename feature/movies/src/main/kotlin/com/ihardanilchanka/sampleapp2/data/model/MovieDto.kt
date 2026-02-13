package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.MovieEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.SimilarMovieEntity
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie
import com.squareup.moshi.Json
import java.util.Date

data class MovieDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "overview") val overview: String,
    @param:Json(name = "release_date") val releaseDate: Date?,
    @param:Json(name = "title") val title: String,
    @param:Json(name = "backdrop_path") val backdropPath: String?,
    @param:Json(name = "vote_count") val voteCount: Int,
    @param:Json(name = "vote_average") val voteAverage: Double,
    @param:Json(name = "genre_ids") val genreIds: List<Int>,
) {

    fun toRawMovie() = RawMovie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
    )

    fun toEntity(order: Int) = MovieEntity(
        id = id,
        backdropPath = backdropPath,
        genreIds = genreIds,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
        sortOrder = order
    )

    fun toSimilarMovieEntity(movieId: Int, order: Int) = SimilarMovieEntity(
        similarTo = movieId,
        movieId = id,
        backdropPath = backdropPath,
        genreIds = genreIds,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
        sortOrder = order
    )
}
