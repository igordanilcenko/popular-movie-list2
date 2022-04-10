package com.ihardanilchanka.sampleapp2.domain.usecase

import com.ihardanilchanka.sampleapp2.domain.model.ImageConfig
import com.ihardanilchanka.sampleapp2.domain.repository.ConfigRepository

class LoadConfigUseCase(
    private val configRepository: ConfigRepository,
) : SuspendNoArgsUseCase<ImageConfig> {
    override suspend fun invoke() = configRepository.loadConfig().toModel()
}