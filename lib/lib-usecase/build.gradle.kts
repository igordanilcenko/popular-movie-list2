plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        minSdk = (property("sdk.min") as String).toInt()
    }
    namespace = "com.ihardanilchanka.sampleapp2.lib.usecase"
}

kotlin {
    jvmToolchain(17)
}
