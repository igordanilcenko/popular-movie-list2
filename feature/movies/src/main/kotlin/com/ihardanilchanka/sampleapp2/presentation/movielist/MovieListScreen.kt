package com.ihardanilchanka.sampleapp2.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.LoadingState.*
import com.ihardanilchanka.sampleapp2.OnBottomReached
import com.ihardanilchanka.sampleapp2.component.*
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.movies.R
import com.ihardanilchanka.sampleapp2.toYear
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieListScreen(viewModel: MovieListViewModel = getViewModel()) {
    val uiState by viewModel.movieListUiState.collectAsState()

    Scaffold(
        topBar = { Toolbar(stringResource(R.string.screen_title_movie_list)) },
        content = { Content(viewModel, uiState) },
    )
}

@Composable
fun Content(viewModel: MovieListViewModel, uiState: MovieListUiState) {
    when (uiState.loadingState) {
        is Ready -> {
            Success(
                movies = uiState.movies,
                additionalLoadingState = uiState.additionalLoadingState,
                onMovieItemClicked = { movie -> viewModel.onMovieSelected(movie) },
                onBottomReached = { viewModel.onNeedLoadMore() },
                onReloadClicked = { viewModel.onReloadClicked() },
                onRefresh = { viewModel.onSwipeToRefresh() },
                isRefreshing = uiState.isRefreshing,
            )
        }
        is Loading -> {
            BasicLoading()
        }
        is Error -> {
            BasicError(
                error = uiState.loadingState.error,
                onReloadClicked = { viewModel.onReloadClicked() },
            )
        }
    }
}

@Composable
fun Success(
    movies: List<Movie>,
    additionalLoadingState: LoadingState,
    onBottomReached: () -> Unit,
    onMovieItemClicked: (Movie) -> Unit,
    onReloadClicked: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    val listState = rememberLazyListState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh,
    ) {
        LazyColumn(state = listState) {
            itemsIndexed(movies) { index, movie ->
                MovieItem(index + 1, movie, onMovieItemClicked)
            }
            item {
                BottomItem(loadingState = additionalLoadingState, onReloadClicked = onReloadClicked)
            }
        }
    }

    listState.OnBottomReached {
        onBottomReached()
    }
}

@Composable
fun MovieItem(index: Int, movie: Movie, onMovieItemClicked: (Movie) -> Unit) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onMovieItemClicked(movie) }
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.width(16.dp))

            CoilImage(
                imageUrl = movie.posterUrl,
                modifier = Modifier
                    .height(96.dp)
                    .aspectRatio(2 / 3f),
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${movie.title} (${movie.releaseDate?.toYear() ?: "TBA"})",
                    style = MaterialTheme.typography.subtitle2,
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.genreNames.joinToString(prefix = "(", postfix = ")"),
                    style = MaterialTheme.typography.caption,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(
                        rating = movie.voteAverage.toFloat() / 2,
                        Modifier.height(12.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = movie.voteAverage.toString(),
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
        DividerDark(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
        )
    }
}

@Composable
fun BottomItem(loadingState: LoadingState, onReloadClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        when (loadingState) {
            is Ready -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.movie_list_reach_the_end),
                    style = MaterialTheme.typography.body2,
                )
            }
            is Loading -> {
                BasicLoading()
            }
            is Error -> {
                BasicError(error = loadingState.error, onReloadClicked = onReloadClicked)
            }
        }
    }
}