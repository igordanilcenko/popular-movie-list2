package com.ihardanilchanka.sampleapp2.domain.repository

import com.ihardanilchanka.sampleapp2.domain.model.ImageConfig

interface ConfigRepository {
    suspend fun loadConfig(): ImageConfig
}