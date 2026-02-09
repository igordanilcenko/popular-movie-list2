package com.ihardanilchanka.sampleapp2.presentation.moviedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleapp2.LoadingState.Error
import com.ihardanilchanka.sampleapp2.LoadingState.Loading
import com.ihardanilchanka.sampleapp2.LoadingState.Ready
import com.ihardanilchanka.sampleapp2.component.BasicError
import com.ihardanilchanka.sampleapp2.component.BasicLoading
import com.ihardanilchanka.sampleapp2.component.CoilImage
import com.ihardanilchanka.sampleapp2.component.GradientDark
import com.ihardanilchanka.sampleapp2.component.RatingBar
import com.ihardanilchanka.sampleapp2.component.Toolbar
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.Review
import com.ihardanilchanka.sampleapp2.movies.R
import com.ihardanilchanka.sampleapp2.toYear
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailScreen(viewModel: MovieDetailViewModel = koinViewModel()) {
    val uiState by viewModel.movieDetailUiState.collectAsState()

    Scaffold(
        topBar = {
            Toolbar(
                title = uiState.movie?.title,
                navigateUp = { viewModel.navigateUp() },
            )
        }
    ) { innerPadding ->
        Content(
            viewModel = viewModel,
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun Content(
    viewModel: MovieDetailViewModel,
    uiState: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        uiState.movie?.let { movie ->
            item { MovieHeader(movie) }
            item { MovieBasicInfo(movie) }
        }

        uiState.similarMoviesUiState.apply {
            when (loadingState) {
                is Ready ->
                    similarMovies.takeIf { !it.isNullOrEmpty() }?.let { movies ->
                        item {
                            SimilarMovies(
                                similarMovies = movies,
                                onMovieSelected = { viewModel.onMovieSelected(it) }
                            )
                        }
                    } ?: error("Ready state with null data")
                is Loading -> item { BasicLoading(modifier = Modifier.height(160.dp)) }
                is Error ->
                    item {
                        BasicError(
                            modifier = Modifier.height(160.dp),
                            error = loadingState.error,
                            onReloadClicked = { viewModel.onReloadSimilarMoviesClicked() },
                        )
                    }
            }
        }

        uiState.reviewsUiState.apply {
            when (loadingState) {
                is Ready ->
                    reviews.takeIf { !it.isNullOrEmpty() }?.let { reviews ->
                        item { ReviewsHeader() }
                        items(reviews) { review -> ReviewItem(review) }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }
                is Loading -> item { BasicLoading(modifier = Modifier.height(160.dp)) }
                is Error ->
                    item {
                        BasicError(
                            modifier = Modifier.height(160.dp),
                            error = loadingState.error,
                            onReloadClicked = { viewModel.onReloadReviewsClicked() },
                        )
                    }
            }
        }
    }
}

@Composable
fun MovieHeader(movie: Movie) {
    Box {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            imageUrl = movie.backdropUrl,
        )
        GradientDark(Modifier.matchParentSize())
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(16.dp),
        )
    }
}

@Composable
fun MovieBasicInfo(movie: Movie) {
    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = movie.releaseDate?.toYear()?.toString() ?: "TBA",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.genreNames.joinToString(),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(
                    rating = movie.voteAverage.toFloat() / 2,
                    Modifier.height(36.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun SimilarMovies(similarMovies: List<Movie>, onMovieSelected: (Movie) -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.movie_detail_similar_movies_title),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
        )
        LazyRow {
            item { Spacer(modifier = Modifier.width(8.dp)) }
            items(similarMovies) { movie ->
                SimilarMovieItem(movie = movie, onMovieItemClicked = { onMovieSelected(movie) })
            }
            item { Spacer(modifier = Modifier.width(8.dp)) }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SimilarMovieItem(movie: Movie, onMovieItemClicked: (Movie) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = { onMovieItemClicked(movie) }
            ),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            CoilImage(
                modifier = Modifier
                    .height(108.dp)
                    .aspectRatio(2 / 3f),
                imageUrl = movie.posterUrl,
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.width(160.dp)) {
                Text(
                    text = "${movie.title} (${movie.releaseDate?.toYear() ?: "TBA"})",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.genreNames.joinToString(
                        prefix = "(",
                        postfix = ")"
                    ),
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
    }
}

@Composable
fun ReviewsHeader() {
    Text(
        text = stringResource(R.string.movie_detail_review_title),
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    )
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = review.author,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
