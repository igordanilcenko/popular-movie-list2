plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        minSdk = (property("sdk.min") as String).toInt()
    }

    buildFeatures {
        compose = true
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.ui"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.coroutines)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.retrofit)

    implementation(libs.coil)
    implementation(libs.coil.network)
}
