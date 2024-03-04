plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dk.onlinestore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dk.onlinestore"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":feature:login"))
    implementation(project(":feature:home"))
    implementation(project(":feature:catalog"))
    implementation(project(":feature:discount"))
    implementation(project(":feature:basket"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:favorite"))
    implementation(project(":core"))
    implementation(project(":api"))

    //Retrofit
    implementation(Retrofit.main)
    implementation(Retrofit.gson_convertor)

    //Koin
    implementation(Koin.koin)

    //Kotlin
    implementation(Kotlin.core)

    //AndroidX
    implementation(AndroidX.appcompat)

    //Design
    implementation(Design.material)
    implementation(Design.constraint_layout)

    //Navigation
    implementation(Navigation.runtime_ktx)
    implementation(Navigation.ui_ktx)
    implementation(Navigation.fragment_ktx)
}