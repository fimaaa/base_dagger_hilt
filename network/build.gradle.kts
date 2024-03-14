plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "${project.rootDir}/library/common/android_common.gradle")

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
    implementation(project(Modules.LOCAL))

    implementation(project(Modules.Library.COMMON))

    implementation(project(Modules.Model.COMMON))
    implementation(project(Modules.Model.CRYPTO))
    implementation(project(Modules.Model.EMPLOYEE))

    implementation(libs.retrofit)
    implementation(libs.retrofitRX)
    implementation(libs.retrofitCoroutines)
    implementation(libs.retrofitConverterGson)
    implementation(libs.retrofitConverterMoshi)
    implementation(libs.rxAndroid)
    implementation(libs.rxJava)

    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.retrofitConverterMoshi)

    implementation(libs.androidXDataStore)

    implementation(platform(libs.okhttpPlatform))
    implementation(libs.okHttp)
    implementation(libs.okhttpLogging)

    // Dagger Hilt
    kaptAndroidTest(libs.dagger)
    // Hilt dependencies
    implementation(libs.daggerHilt)
    kapt(libs.daggerHiltCompiler)

    //HTTP Inspector
    debugImplementation(libs.chuckerActive)
    "qaImplementation"(libs.chuckerActive)
    releaseImplementation(libs.chuckerDisable)

    implementation(libs.gson)

}