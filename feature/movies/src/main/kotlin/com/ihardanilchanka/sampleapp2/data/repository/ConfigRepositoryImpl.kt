package com.ihardanilchanka.sampleapp2.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.model.ImageConfigDto
import com.ihardanilchanka.sampleapp2.domain.repository.ConfigRepository
import com.squareup.moshi.Moshi
import java.net.UnknownHostException

class ConfigRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val moshi: Moshi,
    private val sharedPreferences: SharedPreferences
) : ConfigRepository {

    private var imageConfigCache: ImageConfigDto? = null

    override suspend fun loadConfig() = imageConfigCache ?: try {
        moviesRestInterface.getConfiguration(ApiConfig.API_KEY).imageConfigDto
            .also { saveImageConfig(it) }
    } catch (e: UnknownHostException) {
        getImageConfig() ?: throw e
    }.also { imageConfigCache = it }


    private fun saveImageConfig(config: ImageConfigDto) {
        sharedPreferences.edit {
            putString(KEY_CONFIG, moshi.adapter(ImageConfigDto::class.java).toJson(config))
        }
    }

    private fun getImageConfig(): ImageConfigDto? {
        return sharedPreferences.getString(KEY_CONFIG, null)?.let { stored ->
            moshi.adapter(ImageConfigDto::class.java).fromJson(stored)
        }
    }

    companion object {
        private const val KEY_CONFIG = "KEY_CONFIG"
    }
}