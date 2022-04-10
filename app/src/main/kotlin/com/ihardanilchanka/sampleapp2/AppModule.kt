package com.ihardanilchanka.sampleapp2

import android.content.Context
import android.content.SharedPreferences
import com.ihardanilchanka.sampleapp2.domain.navigation.MoviesNavigation
import com.ihardanilchanka.sampleapp2.navigation.AppNavigation
import org.koin.dsl.binds
import org.koin.dsl.module

object AppModule {
    fun init() = module {
        single { provideSharedPreferences(get()) }

        single { AppNavigation(get()) } binds arrayOf(
            MoviesNavigation::class,
        )
    }

    private fun provideSharedPreferences(androidContext: Context): SharedPreferences {
        return androidContext.getSharedPreferences("SampleApp", Context.MODE_PRIVATE)
    }
}