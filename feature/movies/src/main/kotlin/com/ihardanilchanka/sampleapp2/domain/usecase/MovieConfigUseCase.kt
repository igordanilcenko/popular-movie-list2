package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import com.ihardanilchanka.sampleapp2.domain.model.Movie

class MovieConfigUseCase(
    private val loadConfig: LoadConfigUseCase,
    private val loadGenreList: LoadGenreListUseCase,
) : SuspendUseCase<List<MovieDto>, List<Movie>> {
    override suspend fun invoke(arg: List<MovieDto>): List<Movie> {
        val config = loadConfig()
        val genres = loadGenreList()
        return arg.map { movieDto ->
            movieDto.toModel(
                imageBaseUrl = config.secureBaseUrl,
                genres = movieDto.genreIds.mapNotNull { id -> genres.find { it.id == id }?.name },
            )
        }
    }
}