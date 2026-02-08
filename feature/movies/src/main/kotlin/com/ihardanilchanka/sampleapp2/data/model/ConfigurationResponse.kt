package com.ihardanilchanka.sampleapp2.data.model

import com.squareup.moshi.Json

data class ConfigurationResponse(
    @param:Json(name = "images") val imageConfigDto: ImageConfigDto
)