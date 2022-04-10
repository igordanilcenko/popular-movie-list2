package com.ihardanilchanka.sampleapp2.presentation.moviedetail

import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.LoadingState.Ready
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.Review

data class MovieDetailUiState(
    val movie: Movie? = null,
    val reviewsUiState: ReviewsUiState = ReviewsUiState(),
    val similarMoviesUiState: SimilarMoviesUiState = SimilarMoviesUiState(),
)

data class SimilarMoviesUiState(
    val similarMovies: List<Movie>? = null,
    val loadingState: LoadingState = Ready,
)

data class ReviewsUiState(
    val reviews: List<Review>? = null,
    val loadingState: LoadingState = Ready,
)
