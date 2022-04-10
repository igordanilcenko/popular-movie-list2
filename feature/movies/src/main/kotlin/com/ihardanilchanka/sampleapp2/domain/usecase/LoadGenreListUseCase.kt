package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.Genre
import com.ihardanilchanka.sampleapp2.domain.repository.GenreRepository


class LoadGenreListUseCase(
    private val genreRepository: GenreRepository,
) : SuspendNoArgsUseCase<List<Genre>> {
    override suspend fun invoke() = genreRepository.loadGenreList().map { it.toModel() }
}