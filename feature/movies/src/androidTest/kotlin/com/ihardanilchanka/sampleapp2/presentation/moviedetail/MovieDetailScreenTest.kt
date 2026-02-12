package com.ihardanilchanka.sampleapp2.presentation.moviedetail

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.resource.AppTheme
import org.junit.Rule
import org.junit.Test
import java.util.Date

class MovieDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val movie = Movie(
        id = 1,
        title = "The Dark Knight",
        overview = "When the menace known as the Joker...",
        releaseDate = Date(1216598400000),
        voteAverage = 8.5,
        posterUrl = null,
        backdropUrl = null,
        genreNames = listOf("Action", "Crime"),
    )

    @Test
    fun movieDetailScreen_movieTitle_isShownInToolbar() {
        composeTestRule.setContent {
            AppTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState(
                        movie = movie,
                        similarMoviesUiState = SimilarMoviesUiState(loadingState = LoadingState.Ready),
                        reviewsUiState = ReviewsUiState(loadingState = LoadingState.Ready),
                    ),
                    onMovieSelected = {},
                    onNavigateUp = {},
                    onReloadSimilarMovies = {},
                    onReloadReviews = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("toolbar_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("toolbar_title").assert(hasText("The Dark Knight"))
    }

    @Test
    fun movieDetailScreen_similarMoviesLoadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            AppTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState(
                        movie = movie,
                        similarMoviesUiState = SimilarMoviesUiState(loadingState = LoadingState.Loading),
                        reviewsUiState = ReviewsUiState(loadingState = LoadingState.Ready),
                    ),
                    onMovieSelected = {},
                    onNavigateUp = {},
                    onReloadSimilarMovies = {},
                    onReloadReviews = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("similar_movies_loading").assertIsDisplayed()
    }

    @Test
    fun movieDetailScreen_reviewsErrorState_showsErrorComponent() {
        composeTestRule.setContent {
            AppTheme {
                MovieDetailScreenContent(
                    uiState = MovieDetailUiState(
                        movie = movie,
                        similarMoviesUiState = SimilarMoviesUiState(loadingState = LoadingState.Ready),
                        reviewsUiState = ReviewsUiState(
                            loadingState = LoadingState.Error(RuntimeException("Network error")),
                        ),
                    ),
                    onMovieSelected = {},
                    onNavigateUp = {},
                    onReloadSimilarMovies = {},
                    onReloadReviews = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("reviews_error").assertIsDisplayed()
    }
}
