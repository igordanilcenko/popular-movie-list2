plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        minSdk = (property("sdk.min") as String).toInt()
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.network"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.coroutines)

    implementation(libs.koin.core)

    api(libs.retrofit)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.moshi.kotlin)
}
