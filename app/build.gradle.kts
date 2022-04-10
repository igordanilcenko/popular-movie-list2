plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Sdk.compile

    defaultConfig {
        applicationId = "com.ihardanilchanka.sampleapp2"
        minSdk = Sdk.min
        targetSdk = Sdk.target
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependency.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Dependency.AndroidX.core)
    implementation(Dependency.material)

    implementation(Dependency.AndroidX.Compose.activity)
    implementation(Dependency.AndroidX.Compose.material)

    implementation(Dependency.Koin.core)
    implementation(Dependency.Koin.android)

    implementation(Dependency.coil)

    implementation(project(Module.Feature.movies))
    implementation(project(Module.Lib.ui))
    implementation(project(Module.Lib.network))
    implementation(project(Module.Lib.navigation))

    implementation(Dependency.AndroidX.Navigation.compose)
}