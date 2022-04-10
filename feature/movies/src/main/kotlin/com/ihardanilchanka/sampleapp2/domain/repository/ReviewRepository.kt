package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.data.model.ReviewDto

interface ReviewRepository {
    suspend fun loadReviewList(movieId: Int): List<ReviewDto>
}