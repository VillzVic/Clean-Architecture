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
