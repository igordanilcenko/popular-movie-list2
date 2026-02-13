package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.domain.model.Review

interface ReviewRepository {
    suspend fun loadReviewList(movieId: Int): List<Review>
}