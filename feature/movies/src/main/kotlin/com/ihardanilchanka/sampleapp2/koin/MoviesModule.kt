package com.ihardanilchanka.sampleapp2.koin

import android.content.Context
import androidx.room.Room
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.MoviesDatabase
import com.ihardanilchanka.sampleapp2.data.repository.ConfigRepositoryImpl
import com.ihardanilchanka.sampleapp2.data.repository.GenreRepositoryImpl
import com.ihardanilchanka.sampleapp2.data.repository.MovieRepositoryImpl
import com.ihardanilchanka.sampleapp2.data.repository.ReviewRepositoryImpl
import com.ihardanilchanka.sampleapp2.domain.navigation.ShowMovieDetailUseCase
import com.ihardanilchanka.sampleapp2.domain.repository.ConfigRepository
import com.ihardanilchanka.sampleapp2.domain.repository.GenreRepository
import com.ihardanilchanka.sampleapp2.domain.repository.MovieRepository
import com.ihardanilchanka.sampleapp2.domain.repository.ReviewRepository
import com.ihardanilchanka.sampleapp2.domain.usecase.LoadConfigUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.LoadGenreListUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.LoadReviewListUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.MovieConfigUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleapp2.domain.usecase.SelectedMovieUseCase
import com.ihardanilchanka.sampleapp2.presentation.moviedetail.MovieDetailViewModel
import com.ihardanilchanka.sampleapp2.presentation.movielist.MovieListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

object MoviesModule {
    fun init() = module {
        single { get<Retrofit>().create(MoviesRestInterface::class.java) }

        single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
        single<GenreRepository> { GenreRepositoryImpl(get(), get()) }
        single<ConfigRepository> { ConfigRepositoryImpl(get(), get(), get()) }
        single<ReviewRepository> { ReviewRepositoryImpl(get(), get()) }

        viewModelOf(::MovieListViewModel)
        viewModelOf(::MovieDetailViewModel)

        factory { MovieUseCase.LoadSimilar(get(), get()) }
        factory { MovieUseCase.LoadPopular(get(), get()) }
        factory { MovieUseCase.RefreshPopular(get(), get()) }
        factory { MovieUseCase.FetchMorePopular(get(), get()) }
        factory { SelectedMovieUseCase.Load(get()) }
        factory { SelectedMovieUseCase.Select(get(), get()) }
        factory { LoadConfigUseCase(get()) }
        factory { LoadGenreListUseCase(get()) }
        factory { LoadReviewListUseCase(get()) }
        factory { MovieConfigUseCase(get(), get()) }

        factory { ShowMovieDetailUseCase(get()) }

        single { provideDatabase(get()) }
        single { get<MoviesDatabase>().genreDao() }
        single { get<MoviesDatabase>().movieDao() }
        single { get<MoviesDatabase>().similarMovieDao() }
        single { get<MoviesDatabase>().reviewDao() }
    }

    private fun provideDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies").build()
    }
}