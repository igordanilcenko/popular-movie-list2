package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.MovieDao
import com.ihardanilchanka.sampleapp2.data.database.dao.SimilarMovieDao
import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie
import com.ihardanilchanka.sampleapp2.domain.repository.MovieRepository
import com.ihardanilchanka.sampleapp2.simulateNetworkDelay
import java.net.UnknownHostException

class MovieRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val similarMovieDao: SimilarMovieDao,
    private val movieDao: MovieDao,
) : MovieRepository {

    private var selectedMovie: Movie? = null

    private var currentPage: Int = 1
    private val popularMoviesCache = mutableListOf<RawMovie>()

    private val similarMoviesCache = mutableMapOf<Int, List<RawMovie>>()

    override fun getSelectedMovie(): Movie =
        selectedMovie ?: error("No movie stored in the MovieRepository!")

    override fun storeSelectedMovie(movie: Movie) {
        selectedMovie = movie
    }

    override suspend fun loadSimilarMovieList(movieId: Int) = similarMoviesCache[movieId] ?: try {
        simulateNetworkDelay()

        val dtos = moviesRestInterface.getSimilarMovieList(movieId, ApiConfig.API_KEY).movies
        similarMovieDao.deleteAll(*similarMovieDao.getAll(movieId).toTypedArray())
        similarMovieDao.insertAll(
            *dtos.mapIndexed { index, movie ->
                movie.toSimilarMovieEntity(movieId, index)
            }.toTypedArray()
        )
        dtos.map { it.toRawMovie() }
    } catch (e: UnknownHostException) {
        similarMovieDao.getAll(movieId).takeIf { it.isNotEmpty() }?.map { it.toRawMovie() }
            ?: throw e
    }.also { similarMoviesCache[movieId] = it }

    override fun getPopularMovieList() = popularMoviesCache

    override suspend fun refreshPopularMovieList(): List<RawMovie> {
        popularMoviesCache.clear()
        currentPage = 1

        simulateNetworkDelay()

        val dtos = moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, 1).movies

        movieDao.deleteAllItems()
        movieDao.insertAll(*dtos.mapIndexed { index, movie -> movie.toEntity(index) }
            .toTypedArray())

        popularMoviesCache.addAll(dtos.map { it.toRawMovie() })
        currentPage++

        return popularMoviesCache
    }

    override suspend fun fetchMorePopularMovies(): List<RawMovie> {
        simulateNetworkDelay()

        popularMoviesCache.addAll(
            try {
                val dtos =
                    moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, currentPage).movies
                // save movies only for 1st page. The app is online-first, so offline work has
                // retained only part of online functionality
                if (currentPage == 1) {
                    movieDao.deleteAllItems()
                    movieDao.insertAll(*dtos.mapIndexed { index, movie ->
                        movie.toEntity(index)
                    }.toTypedArray())
                }
                dtos.map { it.toRawMovie() }
            } catch (e: UnknownHostException) {
                movieDao.getAll().takeIf { currentPage == 1 && it.isNotEmpty() }
                    ?.map { it.toRawMovie() }
                    ?: throw e
            }
        )

        currentPage++
        return popularMoviesCache
    }
}
