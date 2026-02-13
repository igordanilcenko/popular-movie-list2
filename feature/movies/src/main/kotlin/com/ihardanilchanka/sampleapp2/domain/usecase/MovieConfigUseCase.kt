package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie
import com.ihardanilchanka.sampleapp2.domain.model.toMovie

class MovieConfigUseCase(
    private val loadConfig: LoadConfigUseCase,
    private val loadGenreList: LoadGenreListUseCase,
) : SuspendUseCase<List<RawMovie>, List<Movie>> {
    override suspend fun invoke(arg: List<RawMovie>): List<Movie> {
        val config = loadConfig()
        val genres = loadGenreList()
        return arg.map { it.toMovie(config.secureBaseUrl, genres) }
    }
}