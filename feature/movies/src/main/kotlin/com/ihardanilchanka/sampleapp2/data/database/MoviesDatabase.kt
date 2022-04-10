package com.ihardanilchanka.sampleapp2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihardanilchanka.sampleapp2.data.database.dao.GenreDao
import com.ihardanilchanka.sampleapp2.data.database.dao.MovieDao
import com.ihardanilchanka.sampleapp2.data.database.dao.ReviewDao
import com.ihardanilchanka.sampleapp2.data.database.dao.SimilarMovieDao
import com.ihardanilchanka.sampleapp2.data.database.entity.GenreEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.MovieEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.ReviewEntity
import com.ihardanilchanka.sampleapp2.data.database.entity.SimilarMovieEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        SimilarMovieEntity::class,
        ReviewEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun similarMovieDao(): SimilarMovieDao
    abstract fun reviewDao(): ReviewDao
}