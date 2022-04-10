package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.MovieEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.SimilarMovieEntity
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.squareup.moshi.Json
import java.util.*

data class MovieDto(
    val id: Int,
    @Json(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: Date?,
    val title: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "genre_ids")
    val genreIds: List<Int>
) {

    fun toModel(
        imageBaseUrl: String,
        genres: List<String>
    ) = Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        posterUrl = posterPath?.let { getImageUrl(imageBaseUrl, it) },
        backdropUrl = backdropPath?.let { getImageUrl(imageBaseUrl, it) },
        genreNames = genres,
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

const val IMAGE_SIZE_PREFIX = "original"

/**
 * To build an image URL, you will need 3 pieces of data. The base_url, size and file_path.
 * Simply combine them all and you will have a fully qualified URL.
 * More: https://developers.themoviedb.org/3/getting-started/images
 */
private fun getImageUrl(baseUrl: String, filePath: String): String {
    return "$baseUrl/$IMAGE_SIZE_PREFIX/$filePath"
}