package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import com.ihardanilchanka.sampleapp2.domain.model.Movie

interface MovieRepository {
    fun getSelectedMovie(): Movie
    fun storeSelectedMovie(movie: Movie)
    suspend fun loadSimilarMovieList(movieId: Int): List<MovieDto>
    fun getPopularMovieList(): List<MovieDto>
    suspend fun refreshPopularMovieList(): List<MovieDto>
    suspend fun fetchMorePopularMovies(): List<MovieDto>
}