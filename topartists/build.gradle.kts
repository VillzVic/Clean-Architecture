import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.detekt) version(BuildPlugins.Versions.detekt)
}

android {
    compileSdkVersion(AndroidSdk.compile)


    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName ="1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    (kotlinOptions as KotlinJvmOptions).apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
//    implementation fileTree(org.gradle.internal.impldep.bsh.commands.dir: 'libs', include: ['*.jar'])
    implementation(project(ProjectModules.core))

    implementation(Libraries.appCompat)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerSupportAndroid)
    implementation(Libraries.legacySupport)
    implementation(Libraries.glide)
    implementation(Libraries.lifecycle)
    implementation(Libraries.archLifecycle)
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.designLibary)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.workVersion)
    api(Libraries.archRoomRuntime)
    kapt(Libraries.archRoomCompiler)
    api(Libraries.retrofit)
    implementation(Libraries.retrofitMoshi)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)
    kapt(Libraries.glideCompiler)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    implementation(Libraries.ktxCore)
    implementation((Libraries.kotlinStdLib))
}
repositories {
    mavenCentral()
}
