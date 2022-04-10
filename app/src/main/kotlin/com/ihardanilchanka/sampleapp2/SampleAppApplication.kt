package com.ihardanilchanka.sampleapp2

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.ihardanilchanka.sampleapp2.koin.MoviesModule
import com.ihardanilchanka.sampleapp2.koin.NavigationModule
import com.ihardanilchanka.sampleapp2.koin.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleAppApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleAppApplication)
            modules(
                AppModule.init(),
                MoviesModule.init(),
                NetworkModule.init(),
                NavigationModule.init(),
            )
        }
    }

    override fun newImageLoader() = ImageLoader.Builder(this@SampleAppApplication)
        .crossfade(true)
        .memoryCache {
            MemoryCache.Builder(this@SampleAppApplication)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(this@SampleAppApplication.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()
}