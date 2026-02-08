package com.ihardanilchanka.sampleapp2.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleapp2.LoadingState
import com.ihardanilchanka.sampleapp2.LoadingState.Error
import com.ihardanilchanka.sampleapp2.LoadingState.Loading
import com.ihardanilchanka.sampleapp2.LoadingState.Ready
import com.ihardanilchanka.sampleapp2.OnBottomReached
import com.ihardanilchanka.sampleapp2.component.BasicError
import com.ihardanilchanka.sampleapp2.component.BasicLoading
import com.ihardanilchanka.sampleapp2.component.CoilImage
import com.ihardanilchanka.sampleapp2.component.DividerDark
import com.ihardanilchanka.sampleapp2.component.RatingBar
import com.ihardanilchanka.sampleapp2.component.Toolbar
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.movies.R
import com.ihardanilchanka.sampleapp2.toYear
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(viewModel: MovieListViewModel = koinViewModel()) {
    val uiState by viewModel.movieListUiState.collectAsState()

    Scaffold(
        topBar = { Toolbar(stringResource(R.string.screen_title_movie_list)) },
        content = { innerPadding ->
            Content(
                viewModel = viewModel,
                uiState = uiState,
                modifier = Modifier.padding(innerPadding),
            )
        },
    )
}

@Composable
fun Content(
    viewModel: MovieListViewModel,
    uiState: MovieListUiState,
    modifier: Modifier = Modifier,
) {
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
                modifier = modifier,
            )
        }
        is Loading -> {
            BasicLoading(modifier = modifier)
        }
        is Error -> {
            BasicError(
                modifier = modifier,
                error = uiState.loadingState.error,
                onReloadClicked = { viewModel.onReloadClicked() },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Success(
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
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
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
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
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
                style = MaterialTheme.typography.titleSmall,
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
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.genreNames.joinToString(prefix = "(", postfix = ")"),
                    style = MaterialTheme.typography.bodySmall,
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
                        style = MaterialTheme.typography.bodySmall,
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
                    style = MaterialTheme.typography.bodyMedium,
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