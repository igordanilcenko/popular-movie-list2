plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Sdk.compile

    defaultConfig {
        minSdk = Sdk.min
    }
    kotlin {
        jvmToolchain(17)
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.network"
}

dependencies {
    implementation(Dependency.coroutines)

    implementation(Dependency.Koin.core)

    api(Dependency.Retrofit.retrofit)
    implementation(Dependency.Retrofit.loggingInterceptor)
    implementation(Dependency.Retrofit.converterMoshi)

    implementation(Dependency.Moshi.kotlin)
}
