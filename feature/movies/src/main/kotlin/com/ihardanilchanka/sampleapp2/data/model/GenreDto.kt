package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.data.database.entity.GenreEntity
import com.ihardanilchanka.sampleapp2.domain.model.Genre
import com.squareup.moshi.Json

data class GenreDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String
) {

    fun toModel() = Genre(id = id, name = name)

    fun toEntity() = GenreEntity(id = id, name = name)
}
