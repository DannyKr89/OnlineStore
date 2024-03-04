object Versions {
    //Kotlin
    const val core_ktx = "1.12.0"

    //AndroidX
    const val appcompat = "1.6.1"

    //Design
    const val material = "1.11.0"
    const val constraint_layout = "2.1.4"

    //Retrofit
    const val retrofit_main = "2.9.0"

    //Navigation
    const val navigation = "2.7.7"

    //Koin
    const val koin = "3.5.3"

    //Gson
    const val gson = "2.10.1"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core_ktx}"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
}

object Design {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
}

object Retrofit {
    const val main = "com.squareup.retrofit2:retrofit:${Versions.retrofit_main}"
    const val gson_convertor = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_main}"
}

object Navigation {
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
}

object Koin {
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
}

object Gson {
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}



