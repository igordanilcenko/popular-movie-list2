package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.Movie
import com.ihardanilchanka.sampleapp2.domain.model.Review
import com.ihardanilchanka.sampleapp2.domain.repository.ReviewRepository

class LoadReviewListUseCase(
    private val reviewRepository: ReviewRepository,
) : SuspendUseCase<Movie, List<Review>> {
    override suspend fun invoke(arg: Movie): List<Review> {
        return reviewRepository.loadReviewList(movieId = arg.id).map { it.toModel() }
    }
}