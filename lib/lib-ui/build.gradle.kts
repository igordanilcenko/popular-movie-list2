plugins {
    id("com.android.library")
    kotlin("android")
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

    implementation(Dependency.AndroidX.Compose.ui)
    implementation(Dependency.AndroidX.Compose.foundation)
    implementation(Dependency.AndroidX.Compose.material)

    implementation(Dependency.Retrofit.retrofit)

    implementation(Dependency.coil)
}
