
plugins{
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.detekt) version (BuildPlugins.Versions.detekt)
}


android {
    compileSdkVersion(AndroidSdk.compile)


    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  //important, setting a variable

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
//    implementation(fileTree(org.gradle.internal.impldep.bsh.commands.dir: 'libs', include: ['*.jar']))

    implementation(Libraries.kotlinStdLib)
    api(Libraries.okHttp)
    api(Libraries.loggingInterceptor)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.lifecycle)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)

    testImplementation(TestLibraries.junit4)
}

repositories {
    mavenCentral()
}

detekt {
    version = BuildPlugins.Versions.detekt
    input = files("src/main/java", "src/androidx/java", "src/support/java")
    filters = ".*test.*,.*/resources/.*,.*/tmp/.*"
}