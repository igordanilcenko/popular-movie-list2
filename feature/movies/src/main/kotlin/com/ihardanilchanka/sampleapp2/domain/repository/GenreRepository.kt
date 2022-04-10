package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.data.model.GenreDto

interface GenreRepository {
    suspend fun loadGenreList(): List<GenreDto>
}