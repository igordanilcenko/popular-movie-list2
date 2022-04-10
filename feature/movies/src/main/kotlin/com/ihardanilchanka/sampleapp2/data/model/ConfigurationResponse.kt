package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class ConfigurationResponse(
    @Json(name = "images") val imageConfigDto: ImageConfigDto
)