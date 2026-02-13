package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihardanilchanka.sampleapp2.domain.model.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
) {

    fun toModel() = Genre(id = id, name = name)
}
