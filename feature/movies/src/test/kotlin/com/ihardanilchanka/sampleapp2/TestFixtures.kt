package com.ihardanilchanka.sampleapp2

import com.ihardanilchanka.sampleapp2.data.database.entity.GenreEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.MovieEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.SimilarMovieEntity
import com.ihardanilchanka.sampleapp2.data.model.GenreDto
import com.ihardanilchanka.sampleapp2.data.model.ImageConfigDto
import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import com.ihardanilchanka.sampleapp2.data.model.ReviewDto
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.Review
import java.util.Date

fun fakeMovie(id: Int = 1) = Movie(
    id = id,
    title = "Movie $id",
    overview = "Overview $id",
    releaseDate = Date(0),
    voteAverage = 7.5,
    posterUrl = null,
    backdropUrl = null,
    genreNames = listOf("Action"),
)

fun fakeReview(id: String = "1") = Review(
    id = id,
    author = "Author $id",
    content = "Great movie!",
)

fun fakeGenreDto(id: Int = 1) = GenreDto(id = id, name = "Genre $id")

fun fakeMovieDto(id: Int = 1) = MovieDto(
    id = id,
    posterPath = null,
    overview = "Overview $id",
    releaseDate = null,
    title = "Movie $id",
    backdropPath = null,
    voteCount = 100,
    voteAverage = 7.0,
    genreIds = listOf(28),
)

fun fakeReviewDto(id: String = "r1") = ReviewDto(
    id = id,
    author = "Author $id",
    content = "Great movie!",
)

fun fakeImageConfigDto() = ImageConfigDto(
    baseUrl = "http://img.tmdb.org/",
    secureBaseUrl = "https://img.tmdb.org/",
)

fun fakeGenreEntity(id: Int = 1) = GenreEntity(id = id, name = "Genre $id")

fun fakeMovieEntity(id: Int = 1) = MovieEntity(
    id = id,
    posterPath = null,
    overview = "Overview $id",
    releaseDate = null,
    title = "Movie $id",
    backdropPath = null,
    voteCount = 100,
    voteAverage = 7.0,
    genreIds = listOf(28),
    sortOrder = 0,
)

fun fakeSimilarMovieEntity(similarTo: Int = 1, movieId: Int = 1) = SimilarMovieEntity(
    similarTo = similarTo,
    movieId = movieId,
    posterPath = null,
    overview = "Overview",
    releaseDate = null,
    title = "Movie $movieId",
    backdropPath = null,
    voteCount = 100,
    voteAverage = 7.0,
    genreIds = listOf(28),
    sortOrder = 0,
)
