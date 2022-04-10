package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihardanilchanka.sampleapp2.data.model.GenreDto

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
) {

    fun toDto() = GenreDto(id = id, name = name)
}