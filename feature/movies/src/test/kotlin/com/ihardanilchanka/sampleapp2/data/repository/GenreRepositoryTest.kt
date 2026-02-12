package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.GenreDao
import com.ihardanilchanka.sampleapp2.data.model.GenreListResponse
import com.ihardanilchanka.sampleapp2.fakeGenreDto
import com.ihardanilchanka.sampleapp2.fakeGenreEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GenreRepositoryTest {

    private val api = mockk<MoviesRestInterface>()
    private val genreDao = mockk<GenreDao>(relaxed = true)

    private fun createRepo() = GenreRepositoryImpl(api, genreDao)

    @Test
    fun `loadGenreList uses memory cache and skips network on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getGenreList(any()) } returns GenreListResponse(listOf(fakeGenreDto()))

        repo.loadGenreList()
        repo.loadGenreList()

        coVerify(exactly = 1) { api.getGenreList(any()) }
    }

    @Test
    fun `loadGenreList falls back to db when offline`() = runTest {
        val repo = createRepo()
        coEvery { api.getGenreList(any()) } throws UnknownHostException()
        coEvery { genreDao.getAll() } returns listOf(fakeGenreEntity(id = 28))

        val result = repo.loadGenreList()

        assertEquals(1, result.size)
        assertEquals(28, result[0].id)
    }

    @Test
    fun `loadGenreList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getGenreList(any()) } throws UnknownHostException()
        coEvery { genreDao.getAll() } returns emptyList()

        assertFailsWith<UnknownHostException> {
            repo.loadGenreList()
        }
    }
}
