

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.detekt) version (BuildPlugins.Versions.detekt)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId  = "com.vic.villz.artists"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
//    implementation fileTree(org.gradle.internal.impldep.bsh.commands.dir: 'libs', include: ['*.jar'])

    implementation(project(ProjectModules.core))
    implementation(project(ProjectModules.topartists))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerSupportAndroid)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)
    implementation(Libraries.constraintLayout)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
}

detekt {
    version = BuildPlugins.Versions.detekt
    input = files("src/main/java", "src/androidx/java", "src/support/java")
    filters = ".*test.*,.*/resources/.*,.*/tmp/.*"
}
