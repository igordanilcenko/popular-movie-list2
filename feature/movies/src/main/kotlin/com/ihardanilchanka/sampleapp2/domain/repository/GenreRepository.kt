package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.domain.model.Genre

interface GenreRepository {
    suspend fun loadGenreList(): List<Genre>
}