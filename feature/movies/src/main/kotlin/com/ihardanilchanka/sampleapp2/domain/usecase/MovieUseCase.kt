package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.repository.MovieRepository


class MovieUseCase {
    class LoadSimilar(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendUseCase<Movie, List<Movie>> {
        override suspend fun invoke(arg: Movie) =
            mapToMovieList(movieRepository.loadSimilarMovieList(arg.id))
    }

    class LoadPopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.getPopularMovieList())
    }

    class RefreshPopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.refreshPopularMovieList())
    }

    class FetchMorePopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.fetchMorePopularMovies())
    }
}