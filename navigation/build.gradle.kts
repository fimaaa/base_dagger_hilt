plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}
apply(from = "${project.rootDir}/library/common/android_common.gradle")

android {
    namespace = "com.baseapp.navigation"
}

dependencies {
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
}