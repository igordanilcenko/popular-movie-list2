package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.MovieDao
import com.ihardanilchanka.sampleapp2.data.database.dao.SimilarMovieDao
import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import com.ihardanilchanka.sampleapp2.domain.model.Movie
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
    private val popularMoviesCache = mutableListOf<MovieDto>()

    private val similarMoviesCache = mutableMapOf<Int, List<MovieDto>>()

    override fun getSelectedMovie(): Movie =
        selectedMovie ?: error("No movie stored in the MovieRepository!")

    override fun storeSelectedMovie(movie: Movie) {
        selectedMovie = movie
    }

    override suspend fun loadSimilarMovieList(movieId: Int) = similarMoviesCache[movieId] ?: try {
        simulateNetworkDelay()

        moviesRestInterface.getSimilarMovieList(movieId, ApiConfig.API_KEY).movies
            .also { similarMovies ->
                similarMovieDao.deleteAll(*similarMovieDao.getAll(movieId).toTypedArray())
                similarMovieDao.insertAll(
                    *similarMovies.mapIndexed { index, movie ->
                        movie.toSimilarMovieEntity(movieId, index)
                    }.toTypedArray()
                )
            }
    } catch (e: UnknownHostException) {
        similarMovieDao.getAll(movieId).takeIf { it.isNotEmpty() }?.map { it.toDto() } ?: throw e
    }.also { similarMoviesCache[movieId] = it }

    override fun getPopularMovieList() = popularMoviesCache

    override suspend fun refreshPopularMovieList(): List<MovieDto> {
        popularMoviesCache.clear()
        currentPage = 1

        simulateNetworkDelay()

        val movies = moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, 1).movies

        movieDao.deleteAllItems()
        movieDao.insertAll(*movies.mapIndexed { index, movie -> movie.toEntity(index) }
            .toTypedArray())

        popularMoviesCache.addAll(movies)
        currentPage++

        return popularMoviesCache
    }

    override suspend fun fetchMorePopularMovies(): List<MovieDto> {
        simulateNetworkDelay()

        popularMoviesCache.addAll(
            try {
                moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, currentPage).movies
                    .also { newMovies ->
                        // save movies only for 1st page. The app is online-first, so offline work has
                        // retained only part of online functionality
                        if (currentPage == 1) {
                            movieDao.deleteAllItems()
                            movieDao.insertAll(*newMovies.mapIndexed { index, movie ->
                                movie.toEntity(index)
                            }.toTypedArray())
                        }
                    }
            } catch (e: UnknownHostException) {
                movieDao.getAll().takeIf { currentPage == 1 && it.isNotEmpty() }?.map { it.toDto() }
                    ?: throw e
            }
        )

        currentPage++
        return popularMoviesCache
    }
}
