package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.data.model.ImageConfigDto

interface ConfigRepository {
    suspend fun loadConfig(): ImageConfigDto
}