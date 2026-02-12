package com.ihardanilchanka.sampleapp2.presentation.movielist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.LoadingState.Error
import com.ihardanilchanka.sampleapp2.LoadingState.Loading
import com.ihardanilchanka.sampleapp2.LoadingState.Ready
import com.ihardanilchanka.sampleapp2.OnBottomReached
import com.ihardanilchanka.sampleapp2.component.BasicError
import com.ihardanilchanka.sampleapp2.component.BasicLoading
import com.ihardanilchanka.sampleapp2.component.Toolbar
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.movies.R
import com.ihardanilchanka.sampleapp2.presentation.movielist.widgets.BottomItem
import com.ihardanilchanka.sampleapp2.presentation.movielist.widgets.MovieItem
import com.ihardanilchanka.sampleapp2.presentation.previewMovies
import com.ihardanilchanka.sampleapp2.resource.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(viewModel: MovieListViewModel = koinViewModel()) {
    val uiState by viewModel.movieListUiState.collectAsState()

    MovieListScreenContent(
        uiState = uiState,
        onMovieItemClicked = { movie -> viewModel.onMovieSelected(movie) },
        onBottomReached = { viewModel.onNeedLoadMore() },
        onReloadClicked = { viewModel.onReloadClicked() },
        onRefresh = { viewModel.onSwipeToRefresh() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieListScreenContent(
    uiState: MovieListUiState,
    onMovieItemClicked: (Movie) -> Unit,
    onBottomReached: () -> Unit,
    onReloadClicked: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { Toolbar(title = stringResource(R.string.screen_title_movie_list)) },
    ) { innerPadding ->
        when (uiState.loadingState) {
            is Ready -> {
                MovieList(
                    modifier = Modifier
                        .padding(innerPadding)
                        .testTag("movie_list_content"),
                    movies = uiState.movies,
                    additionalLoadingState = uiState.additionalLoadingState,
                    onMovieItemClicked = onMovieItemClicked,
                    onBottomReached = onBottomReached,
                    onReloadClicked = onReloadClicked,
                    onRefresh = onRefresh,
                    isRefreshing = uiState.isRefreshing,
                )
            }
            is Loading -> {
                BasicLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .testTag("movie_list_loading")
                )
            }
            is Error -> {
                BasicError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .testTag("movie_list_error"),
                    error = uiState.loadingState.error,
                    onReloadClicked = onReloadClicked,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieList(
    movies: List<Movie>,
    additionalLoadingState: LoadingState,
    onBottomReached: () -> Unit,
    onMovieItemClicked: (Movie) -> Unit,
    onReloadClicked: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(state = listState) {
            itemsIndexed(movies) { index, movie ->
                MovieItem(
                    index = index + 1,
                    movie = movie,
                    onMovieItemClicked = onMovieItemClicked,
                )
            }
            item {
                BottomItem(
                    loadingState = additionalLoadingState,
                    onReloadClicked = onReloadClicked,
                )
            }
        }
    }

    listState.OnBottomReached {
        onBottomReached()
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListScreenSuccessPreview() {
    AppTheme {
        MovieListScreenContent(
            uiState = MovieListUiState(
                movies = previewMovies,
                loadingState = Ready,
                additionalLoadingState = Ready,
            ),
            onMovieItemClicked = {},
            onBottomReached = {},
            onReloadClicked = {},
            onRefresh = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListScreenLoadingPreview() {
    AppTheme {
        MovieListScreenContent(
            uiState = MovieListUiState(loadingState = Loading),
            onMovieItemClicked = {},
            onBottomReached = {},
            onReloadClicked = {},
            onRefresh = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListScreenErrorPreview() {
    AppTheme {
        MovieListScreenContent(
            uiState = MovieListUiState(
                loadingState = Error(RuntimeException("Network error")),
            ),
            onMovieItemClicked = {},
            onBottomReached = {},
            onReloadClicked = {},
            onRefresh = {},
        )
    }
}
