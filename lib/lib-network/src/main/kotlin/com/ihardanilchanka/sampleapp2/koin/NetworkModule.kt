package com.ihardanilchanka.sampleapp2.koin

import com.ihardanilchanka.sampleapp2.ApiConfig
import com.ihardanilchanka.sampleapp2.DateJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object NetworkModule {
    fun init() = module {
        single { provideMoshi() }
        single { provideRetrofit(get()) }
    }

    private fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, DateJsonAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private fun provideRetrofit(moshi: Moshi): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(ApiConfig.ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .build()
    }
}