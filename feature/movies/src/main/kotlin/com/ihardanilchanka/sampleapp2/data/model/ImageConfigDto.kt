package com.ihardanilchanka.sampleapp2.data.model

import com.ihardanilchanka.sampleapp2.domain.model.ImageConfig
import com.squareup.moshi.Json

data class ImageConfigDto(
    @Json(name = "base_url")
    val baseUrl: String,
    @Json(name = "secure_base_url")
    val secureBaseUrl: String
) {

    fun toModel() = ImageConfig(baseUrl = baseUrl, secureBaseUrl = secureBaseUrl)
}