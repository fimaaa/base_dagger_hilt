plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "${project.rootDir}/common/android_common.gradle")

android {
    namespace = "com.network.crpyto"

    externalNativeBuild {
        cmake {
            path =  file("CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.modelCrypto))
    implementation(project(Modules.modelEmployee))

    implementation(LibraryAndroid.retrofit)
    implementation(LibraryAndroid.retrofitRX)
    implementation(LibraryAndroid.retrofitCoroutines)
    implementation(LibraryAndroid.retrofitConverterGson)
    implementation(LibraryAndroid.retrofitConverterMoshi)
    implementation(LibraryAndroid.rxAndroid)
    implementation(LibraryAndroid.rxJava)

    implementation(platform(LibraryAndroid.okhttpPlatform))
    implementation(LibraryAndroid.okHttp)
    implementation(LibraryAndroid.okhttpLogging)

    // Dagger Hilt
    kaptAndroidTest(LibraryAndroidTesting.dagger)
    // Hilt dependencies
    implementation(LibraryAndroid.daggerHilt)
    kapt(LibraryAndroid.daggerHiltCompiler)

    //HTTP Inspector
    debugImplementation(LibraryAndroid.chuckerActive)
    "stagingImplementation"(LibraryAndroid.chuckerActive)
    releaseImplementation(LibraryAndroid.chuckerDisable)

    implementation(LibraryAndroid.gson)

}