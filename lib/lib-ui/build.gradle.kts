plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Sdk.compile

    defaultConfig {
        minSdk = Sdk.min
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependency.AndroidX.Compose.version
    }
    kotlin {
        jvmToolchain(17)
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.ui"
}

dependencies {
    implementation(Dependency.coroutines)

    implementation(Dependency.AndroidX.Compose.ui)
    implementation(Dependency.AndroidX.Compose.foundation)
    implementation(Dependency.AndroidX.Compose.material)

    implementation(Dependency.Retrofit.retrofit)

    implementation(Dependency.coil)
}
