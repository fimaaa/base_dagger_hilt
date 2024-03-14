plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply("${project.rootDir}/library/common/android_common.gradle")

android {
    namespace = "com.model.crypto"
}

dependencies {
    implementation(project(Modules.Model.COMMON))

    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.gson)

    implementation(libs.roomRuntime)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)
    implementation(libs.room)
    testImplementation(libs.roomTesting)
}