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
}

dependencies {
    implementation(Dependency.coroutines)
    implementation(Dependency.material)

    implementation(Dependency.AndroidX.core)
    implementation(Dependency.AndroidX.Lifecycle.runtime)
    implementation(Dependency.AndroidX.Navigation.compose)

    implementation(Dependency.Koin.core)

    implementation(project(Module.Lib.usecase))
}
