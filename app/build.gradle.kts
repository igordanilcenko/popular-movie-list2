plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = (property("sdk.compile") as String).toInt()

    defaultConfig {
        applicationId = "com.ihardanilchanka.sampleapp2"
        minSdk = (property("sdk.min") as String).toInt()
        targetSdk = (property("sdk.target") as String).toInt()
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = "com.ihardanilchanka.sampleapp2"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.material3)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.coil)
    implementation(libs.coil.network)

    implementation(project(":feature:movies"))
    implementation(project(":lib:lib-ui"))
    implementation(project(":lib:lib-network"))
    implementation(project(":lib:lib-navigation"))

    implementation(libs.androidx.navigation.compose)
}
