object Dependency {

    const val material = "com.google.android.material:material:1.5.0"
    const val coil = "io.coil-kt:coil-compose:2.0.0-rc02"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
    const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.23.1"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.12.0"

        object Lifecycle {
            private const val version = "2.6.2"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Compose {
            const val version = "1.5.3"
            const val activity = "androidx.activity:activity-compose:1.4.0"
            const val ui = "androidx.compose.ui:ui:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
        }

        object Navigation {
            private const val version = "2.7.4"
            const val compose = "androidx.navigation:navigation-compose:$version"
        }
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.11.0"
    }

    object Room {
        private const val version = "2.5.2"
        const val runtime = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"
        const val compiler = "androidx.room:room-compiler:$version"
    }

    object Moshi {
        private const val version = "1.15.0"
        const val kotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    object Koin {
        private const val version = "3.5.0"
        const val core = "io.insert-koin:koin-core:$version"
        const val android = "io.insert-koin:koin-android:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$version"
    }
}