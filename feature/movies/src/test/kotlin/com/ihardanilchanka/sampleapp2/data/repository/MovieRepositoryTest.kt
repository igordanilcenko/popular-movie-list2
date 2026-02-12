package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.MovieDao
import com.ihardanilchanka.sampleapp2.data.database.dao.SimilarMovieDao
import com.ihardanilchanka.sampleapp2.data.model.MovieListResponse
import com.ihardanilchanka.sampleapp2.fakeMovieDto
import com.ihardanilchanka.sampleapp2.fakeMovieEntity
import com.ihardanilchanka.sampleapp2.fakeSimilarMovieEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MovieRepositoryTest {

    private val api = mockk<MoviesRestInterface>()
    private val similarMovieDao = mockk<SimilarMovieDao>(relaxed = true)
    private val movieDao = mockk<MovieDao>(relaxed = true)

    private fun createRepo() = MovieRepositoryImpl(api, similarMovieDao, movieDao)

    @Test
    fun `loadSimilarMovieList uses memory cache and skips network on second call for same id`() =
        runTest {
            val repo = createRepo()
            coEvery { api.getSimilarMovieList(1, any()) } returns
                    MovieListResponse(1, listOf(fakeMovieDto()), 1, 1)

            repo.loadSimilarMovieList(1)
            repo.loadSimilarMovieList(1)

            coVerify(exactly = 1) { api.getSimilarMovieList(1, any()) }
        }

    @Test
    fun `loadSimilarMovieList falls back to db when offline`() = runTest {
        val repo = createRepo()
        coEvery { api.getSimilarMovieList(1, any()) } throws UnknownHostException()
        coEvery { similarMovieDao.getAll(1) } returns
                listOf(fakeSimilarMovieEntity(similarTo = 1, movieId = 42))

        val result = repo.loadSimilarMovieList(1)

        assertEquals(1, result.size)
        assertEquals(42, result[0].id)
    }

    @Test
    fun `loadSimilarMovieList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getSimilarMovieList(1, any()) } throws UnknownHostException()
        coEvery { similarMovieDao.getAll(1) } returns emptyList()

        assertFailsWith<UnknownHostException> {
            repo.loadSimilarMovieList(1)
        }
    }

    @Test
    fun `fetchMorePopularMovies does not fall back to db when network fails on page 2`() = runTest {
        val repo = createRepo()
        coEvery { api.getPopularMovieList(any(), 1) } returns
                MovieListResponse(1, listOf(fakeMovieDto(1)), 2, 2)
        coEvery { api.getPopularMovieList(any(), 2) } throws UnknownHostException()
        coEvery { movieDao.getAll() } returns listOf(fakeMovieEntity(99))

        repo.fetchMorePopularMovies()

        assertFailsWith<UnknownHostException> {
            repo.fetchMorePopularMovies()
        }
    }

    @Test
    fun `fetchMorePopularMovies accumulates results across pages`() = runTest {
        val repo = createRepo()
        coEvery { api.getPopularMovieList(any(), 1) } returns
                MovieListResponse(1, listOf(fakeMovieDto(1)), 2, 2)
        coEvery { api.getPopularMovieList(any(), 2) } returns
                MovieListResponse(2, listOf(fakeMovieDto(2)), 2, 2)

        repo.fetchMorePopularMovies()
        val result = repo.fetchMorePopularMovies()

        assertEquals(2, result.size)
    }

    @Test
    fun `refreshPopularMovieList resets pagination so next fetch starts from page 1`() = runTest {
        val repo = createRepo()
        coEvery { api.getPopularMovieList(any(), any()) } returns
                MovieListResponse(1, listOf(fakeMovieDto()), 1, 1)

        repo.fetchMorePopularMovies()
        repo.fetchMorePopularMovies()
        repo.refreshPopularMovieList()

        repo.fetchMorePopularMovies()

        coVerify(exactly = 2) { api.getPopularMovieList(any(), 1) }
    }
}
