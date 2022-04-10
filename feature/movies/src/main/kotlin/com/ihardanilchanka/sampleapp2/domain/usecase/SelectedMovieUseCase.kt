package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.navigation.ShowMovieDetailUseCase
import com.ihardanilchanka.sampleapp2.domain.repository.MovieRepository

class SelectedMovieUseCase {
    class Load(
        private val movieRepository: MovieRepository,
    ) : NoArgsUseCase<Movie> {
        override fun invoke() = movieRepository.getSelectedMovie()
    }

    class Select(
        private val movieRepository: MovieRepository,
        private val showMovieDetail: ShowMovieDetailUseCase,
    ) : UseCase<Movie, Unit> {
        override fun invoke(arg: Movie) {
            movieRepository.storeSelectedMovie(arg)
            showMovieDetail()
        }
    }
}