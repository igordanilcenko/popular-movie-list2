plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        minSdk = (property("sdk.min") as String).toInt()
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.navigation"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.material)

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.koin.core)

    implementation(project(":lib:lib-usecase"))
}
