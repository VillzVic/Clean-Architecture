const val kotlinVersion = "1.3.31"

object BuildPlugins {

    object Versions {
        const val androidBuildToolsVersion = "3.4.0-alpha09"
        const val detekt = "1.0.0-RC12"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidBuildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object AndroidSdk {
    const val min = 19
    const val compile = 28
    const val target = compile
}

object ProjectModules {
    const val core = ":core"
    const val topartists = ":topartists"
}

object Libraries {
    private object Versions {
        const val jetpack = "1.1.0-alpha01"
        const val constraintLayout = "2.0.0-alpha3"
        const val ktx = "1.1.0-alpha03"
        const val dagger = "2.24"
        const val legacySupport = "1.0.0"
        const val glide = "4.8.0"
        const val okHttp = "3.12.1"
        const val lifecycle = "2.1.0"
        const val retrofit = "2.5.0"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerSupportAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13-beta-1"
        const val testRunner = "1.2.0"
        const val espressor = "3.2.0"
    }
    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressor}"
}