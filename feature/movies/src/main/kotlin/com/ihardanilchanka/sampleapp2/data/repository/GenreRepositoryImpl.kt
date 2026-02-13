package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.GenreDao
import com.ihardanilchanka.sampleapp2.domain.model.Genre
import com.ihardanilchanka.sampleapp2.domain.repository.GenreRepository
import java.net.UnknownHostException

class GenreRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val genreDao: GenreDao,
) : GenreRepository {

    private var genresCache: List<Genre>? = null

    override suspend fun loadGenreList() = genresCache ?: try {
        val dtos = moviesRestInterface.getGenreList(ApiConfig.API_KEY).genres
        genreDao.deleteAllItems()
        genreDao.insertAll(*dtos.map { it.toEntity() }.toTypedArray())
        dtos.map { it.toModel() }
    } catch (e: UnknownHostException) {
        genreDao.getAll().takeIf { it.isNotEmpty() }?.map { it.toModel() } ?: throw e
    }.also { genresCache = it }
}
