package com.ihardanilchanka.sampleapp2.presentation.movielist

import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.LoadingState.Ready
import com.ihardanilchanka.sampleapp2.domain.model.Movie

data class MovieListUiState(
    val movies: List<Movie> = mutableListOf(),
    val isRefreshing: Boolean = false,
    val loadingState: LoadingState = Ready,
    val additionalLoadingState: LoadingState = Ready,
)