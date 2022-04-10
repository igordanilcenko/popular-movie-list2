package com.ihardanilchanka.sampleapp2.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihardanilchanka.sampleapp2.LoadingState.Error
import com.ihardanilchanka.sampleapp2.LoadingState.Loading
import com.ihardanilchanka.sampleapp2.domain.NavigateUpUseCase
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.usecase.LoadReviewListUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.SelectedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getSelectedMovie: SelectedMovieUseCase.Load,
    private val loadSimilarMovies: MovieUseCase.LoadSimilar,
    private val loadReviews: LoadReviewListUseCase,
    private val goBack: NavigateUpUseCase,
    private val selectMovie: SelectedMovieUseCase.Select,
) : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState(getSelectedMovie()))
    val movieDetailUiState: StateFlow<MovieDetailUiState>
        get() = _movieDetailUiState.asStateFlow()

    init {
        loadSimilarMovies()
        loadReviews()
    }

    fun onMovieSelected(item: Movie) {
        selectMovie(item)
    }

    fun onReloadSimilarMoviesClicked() {
        loadSimilarMovies()
    }

    fun onReloadReviewsClicked() {
        loadReviews()
    }

    fun navigateUp() {
        goBack()
    }

    private fun loadSimilarMovies() {
        viewModelScope.launch {
            _movieDetailUiState.value = _movieDetailUiState.value.copy(
                similarMoviesUiState = SimilarMoviesUiState(loadingState = Loading)
            )

            runCatching {
                loadSimilarMovies(getSelectedMovie())
            }.onSuccess { result ->
                _movieDetailUiState.value = _movieDetailUiState.value.copy(
                    similarMoviesUiState = SimilarMoviesUiState(similarMovies = result)
                )
            }.onFailure { error ->
                error.printStackTrace()
                _movieDetailUiState.value = _movieDetailUiState.value.copy(
                    similarMoviesUiState = SimilarMoviesUiState(loadingState = Error(error))
                )
            }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch {
            _movieDetailUiState.value = _movieDetailUiState.value.copy(
                reviewsUiState = ReviewsUiState(loadingState = Loading)
            )

            runCatching {
                loadReviews(getSelectedMovie())
            }.onSuccess { reviews ->
                _movieDetailUiState.value = _movieDetailUiState.value.copy(
                    reviewsUiState = ReviewsUiState(reviews = reviews)
                )
            }.onFailure { error ->
                error.printStackTrace()
                _movieDetailUiState.value = _movieDetailUiState.value.copy(
                    reviewsUiState = ReviewsUiState(loadingState = Error(error))
                )
            }
        }
    }
}
