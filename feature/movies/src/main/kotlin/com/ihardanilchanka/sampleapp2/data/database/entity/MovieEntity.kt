package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ihardanilchanka.sampleapp2.data.database.MoviesTypeConverters
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie
import java.util.Date

@Entity(tableName = "movie")
@TypeConverters(MoviesTypeConverters::class)
data class MovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: Date?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Int>,
    @ColumnInfo(name = "sort_order") val sortOrder: Int,
) {

    fun toRawMovie() = RawMovie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
    )
}
