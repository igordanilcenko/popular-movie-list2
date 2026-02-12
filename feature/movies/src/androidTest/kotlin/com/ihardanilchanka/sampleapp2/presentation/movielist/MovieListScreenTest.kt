package com.ihardanilchanka.sampleapp2.presentation.movielist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.resource.AppTheme
import org.junit.Rule
import org.junit.Test
import java.util.Date

class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val movie = Movie(
        id = 1,
        title = "The Dark Knight",
        overview = "Overview",
        releaseDate = Date(1216598400000), // 2008
        voteAverage = 8.5,
        posterUrl = null,
        backdropUrl = null,
        genreNames = listOf("Action"),
    )

    @Test
    fun movieListScreen_loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(loadingState = LoadingState.Loading),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_loading").assertIsDisplayed()
    }

    @Test
    fun movieListScreen_errorState_showsErrorComponent() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(
                        loadingState = LoadingState.Error(RuntimeException("Network error")),
                    ),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_error").assertIsDisplayed()
    }

    @Test
    fun movieListScreen_readyState_showsMovieList() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(
                        movies = listOf(movie),
                        loadingState = LoadingState.Ready,
                    ),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_content").assertIsDisplayed()
        composeTestRule.onNodeWithText("The Dark Knight (2008)").assertIsDisplayed()
    }
}
