package com.ihardanilchanka.sampleapp2.presentation.moviedetail.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleapp2.component.CoilImage
import com.ihardanilchanka.sampleapp2.component.GradientDark
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.presentation.previewMovie
import com.ihardanilchanka.sampleapp2.resource.AppTheme

@Composable
fun MovieHeader(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            imageUrl = movie.backdropUrl,
        )
        GradientDark(modifier = Modifier.matchParentSize())
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

@Preview(showBackground = true)
@Composable
private fun MovieHeaderPreview() {
    AppTheme {
        MovieHeader(movie = previewMovie)
    }
}
