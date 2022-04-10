package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ihardanilchanka.sampleapp2.data.database.MoviesTypeConverters
import com.ihardanilchanka.sampleapp2.data.model.MovieDto
import java.util.*

@Entity(tableName = "similar_movie")
@TypeConverters(MoviesTypeConverters::class)
data class SimilarMovieEntity(
    @ColumnInfo(name = "similar_to")
    val similarTo: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: Date?,
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo(name = "sort_order")
    val sortOrder: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toDto() = MovieDto(
        id = movieId,
        backdropPath = backdropPath,
        genreIds = genreIds,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}