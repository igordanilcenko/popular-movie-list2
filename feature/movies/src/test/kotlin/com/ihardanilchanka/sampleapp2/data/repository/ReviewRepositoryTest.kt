package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.ReviewDao
import com.ihardanilchanka.sampleapp2.data.model.ReviewListResponse
import com.ihardanilchanka.sampleapp2.fakeReviewDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException
import kotlin.test.assertFailsWith

class ReviewRepositoryTest {

    private val api = mockk<MoviesRestInterface>()
    private val reviewDao = mockk<ReviewDao>(relaxed = true)

    private fun createRepo() = ReviewRepositoryImpl(api, reviewDao)

    @Test
    fun `loadReviewList uses memory cache and skips network on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getMovieReviews(1, any()) } returns
                ReviewListResponse(1, listOf(fakeReviewDto()))

        repo.loadReviewList(1)
        repo.loadReviewList(1)

        coVerify(exactly = 1) { api.getMovieReviews(1, any()) }
    }

    @Test
    fun `loadReviewList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getMovieReviews(1, any()) } throws UnknownHostException()
        coEvery { reviewDao.getAll(1) } returns emptyList()

        assertFailsWith<UnknownHostException> {
            repo.loadReviewList(1)
        }
    }
}
