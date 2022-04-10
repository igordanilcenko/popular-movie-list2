package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.GenreDao
import com.ihardanilchanka.sampleapp2.data.model.GenreDto
import com.ihardanilchanka.sampleapp2.domain.repository.GenreRepository
import java.net.UnknownHostException

class GenreRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val genreDao: GenreDao,
) : GenreRepository {

    private var genresCache: List<GenreDto>? = null

    override suspend fun loadGenreList() = genresCache ?: try {
        moviesRestInterface.getGenreList(ApiConfig.API_KEY).genres
            .also { genres ->
                genreDao.deleteAllItems()
                genreDao.insertAll(*genres.map { it.toEntity() }.toTypedArray())
            }
    } catch (e: UnknownHostException) {
        genreDao.getAll().takeIf { it.isNotEmpty() }?.map { it.toDto() } ?: throw e
    }.also { genresCache = it }
}
