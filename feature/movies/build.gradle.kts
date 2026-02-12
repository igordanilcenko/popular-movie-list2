plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    alias(libs.plugins.ksp)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        minSdk = (property("sdk.min") as String).toInt()
    }

    buildFeatures {
        compose = true
    }
    namespace = "com.ihardanilchanka.sampleapp2.movies"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.coroutines)

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(project(":lib:lib-ui"))
    implementation(project(":lib:lib-usecase"))
    implementation(project(":lib:lib-network"))
    implementation(project(":lib:lib-navigation"))

    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(kotlin("test"))

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
