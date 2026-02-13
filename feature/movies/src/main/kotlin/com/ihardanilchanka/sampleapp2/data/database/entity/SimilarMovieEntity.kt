package com.ihardanilchanka.sampleapp2.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ihardanilchanka.sampleapp2.data.database.MoviesTypeConverters
import com.ihardanilchanka.sampleapp2.domain.model.RawMovie
import java.util.Date

@Entity(tableName = "similar_movie")
@TypeConverters(MoviesTypeConverters::class)
data class SimilarMovieEntity(
    @ColumnInfo(name = "similar_to") val similarTo: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int,
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
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toRawMovie() = RawMovie(
        id = movieId,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
    )
}
