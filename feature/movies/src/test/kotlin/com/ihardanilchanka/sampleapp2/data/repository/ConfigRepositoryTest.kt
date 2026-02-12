package com.ihardanilchanka.sampleapp2.data.repository

import android.content.SharedPreferences
import com.ihardanilchanka.sampleapp2.data.MoviesRestInterface
import com.ihardanilchanka.sampleapp2.data.model.ConfigurationResponse
import com.ihardanilchanka.sampleapp2.fakeImageConfigDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConfigRepositoryTest {

    private val api = mockk<MoviesRestInterface>()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)

    private fun createRepo() = ConfigRepositoryImpl(api, moshi, sharedPreferences)

    @Test
    fun `loadConfig uses memory cache and skips network and shared preferences on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getConfiguration(any()) } returns ConfigurationResponse(fakeImageConfigDto())

        repo.loadConfig()
        repo.loadConfig()

        coVerify(exactly = 1) { api.getConfiguration(any()) }
        // getString is only called in the offline fallback path — must never be reached
        verify(exactly = 0) { sharedPreferences.getString(any(), any()) }
    }

    @Test
    fun `loadConfig falls back to shared preferences when offline`() = runTest {
        val repo = createRepo()
        val config = fakeImageConfigDto()
        val json = moshi.adapter(config.javaClass).toJson(config)
        coEvery { api.getConfiguration(any()) } throws UnknownHostException()
        every { sharedPreferences.getString(any(), any()) } returns json

        val result = repo.loadConfig()

        assertEquals(config.baseUrl, result.baseUrl)
    }

    @Test
    fun `loadConfig rethrows exception when offline and shared preferences is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getConfiguration(any()) } throws UnknownHostException()
        every { sharedPreferences.getString(any(), any()) } returns null

        assertFailsWith<UnknownHostException> {
            repo.loadConfig()
        }
    }
}
