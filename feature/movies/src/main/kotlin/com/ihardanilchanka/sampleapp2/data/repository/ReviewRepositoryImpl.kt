package com.ihardanilchanka.sampleapp2.data.repository

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.database.dao.ReviewDao
import com.ihardanilchanka.sampleapp2.domain.model.Review
import com.ihardanilchanka.sampleapp2.domain.repository.ReviewRepository
import com.ihardanilchanka.sampleapp2.simulateNetworkDelay
import java.net.UnknownHostException

class ReviewRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val reviewDao: ReviewDao,
) : ReviewRepository {
    private val reviewsCache = mutableMapOf<Int, List<Review>>()

    override suspend fun loadReviewList(movieId: Int) = reviewsCache[movieId] ?: try {
        simulateNetworkDelay()

        val dtos = moviesRestInterface.getMovieReviews(movieId, ApiConfig.API_KEY).reviews
        reviewDao.deleteAll(*reviewDao.getAll(movieId).toTypedArray())
        reviewDao.insertAll(
            *dtos.mapIndexed { index, review -> review.toEntity(movieId, index) }.toTypedArray()
        )
        dtos.map { it.toModel() }
    } catch (e: UnknownHostException) {
        reviewDao.getAll(movieId).takeIf { it.isNotEmpty() }?.map { it.toModel() } ?: throw e
    }.also { reviewsCache[movieId] = it }
}