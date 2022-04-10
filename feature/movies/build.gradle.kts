plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Sdk.compile

    defaultConfig {
        minSdk = Sdk.min
        targetSdk = Sdk.target
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependency.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Dependency.coroutines)

    implementation(Dependency.AndroidX.core)
    implementation(Dependency.AndroidX.Lifecycle.viewModel)

    implementation(Dependency.swipeRefresh)

    implementation(Dependency.AndroidX.Compose.ui)
    implementation(Dependency.AndroidX.Compose.foundation)
    implementation(Dependency.AndroidX.Compose.material)

    implementation(Dependency.Room.runtime)
    implementation(Dependency.Room.ktx)
    kapt(Dependency.Room.compiler)

    implementation(Dependency.Moshi.kotlin)
    kapt(Dependency.Moshi.kotlinCodegen)

    implementation(Dependency.Koin.core)
    implementation(Dependency.Koin.android)
    implementation(Dependency.Koin.compose)

    implementation(project(Module.Lib.ui))
    implementation(project(Module.Lib.usecase))
    implementation(project(Module.Lib.network))
    implementation(project(Module.Lib.navigation))

    implementation(Dependency.AndroidX.Navigation.compose)
}
