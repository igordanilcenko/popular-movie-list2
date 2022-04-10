package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.ReviewDao
import com.ihardanilchanka.sampleapp2.data.model.ReviewDto
import com.ihardanilchanka.sampleapp2.domain.repository.ReviewRepository
import com.ihardanilchanka.sampleapp2.randomDelay
import java.net.UnknownHostException

class ReviewRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val reviewDao: ReviewDao,
) : ReviewRepository {
    private val reviewsCache = mutableMapOf<Int, List<ReviewDto>>()

    override suspend fun loadReviewList(movieId: Int) = reviewsCache[movieId] ?: try {
        randomDelay()

        moviesRestInterface.getMovieReviews(movieId, ApiConfig.API_KEY).reviews
            .also { reviews ->
                reviewDao.deleteAll(*reviewDao.getAll(movieId).toTypedArray())
                reviewDao.insertAll(
                    *reviews.mapIndexed { index, review -> review.toEntity(movieId, index) }
                        .toTypedArray()
                )
            }
    } catch (e: UnknownHostException) {
        reviewDao.getAll(movieId).takeIf { it.isNotEmpty() }?.map { it.toDto() } ?: throw e
    }.also { reviewsCache[movieId] = it }
}