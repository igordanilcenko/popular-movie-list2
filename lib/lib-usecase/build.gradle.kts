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
    namespace = "com.ihardanilchanka.sampleapp2.lib.usecase"
}
