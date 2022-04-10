package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.GenreEntity
import com.ihardanilchanka.sampleapp2.domain.model.Genre

data class GenreDto(
    val id: Int,
    val name: String
) {

    fun toModel() = Genre(id = id, name = name)

    fun toEntity() = GenreEntity(id = id, name = name)
}