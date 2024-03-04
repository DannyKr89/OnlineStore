plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dk.detail"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":core"))

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