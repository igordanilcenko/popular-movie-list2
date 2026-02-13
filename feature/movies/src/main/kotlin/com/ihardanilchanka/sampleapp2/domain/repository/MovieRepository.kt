package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie

interface MovieRepository {
    fun getSelectedMovie(): Movie
    fun storeSelectedMovie(movie: Movie)
    suspend fun loadSimilarMovieList(movieId: Int): List<RawMovie>
    fun getPopularMovieList(): List<RawMovie>
    suspend fun refreshPopularMovieList(): List<RawMovie>
    suspend fun fetchMorePopularMovies(): List<RawMovie>
}