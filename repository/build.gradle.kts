plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply("${project.rootDir}/library/common/android_common.gradle")
android {
    namespace = "com.baseapp.repository"
}

dependencies {
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.LOCAL))

    implementation(project(Modules.Library.COMMON))

    implementation(project(Modules.Model.COMMON))
    implementation(project(Modules.Model.CRYPTO))
    implementation(project(Modules.Model.EMPLOYEE))

    implementation(libs.gson)

    implementation(platform(libs.okhttpPlatform))
    implementation(libs.okHttp)

    implementation(libs.androidXDataStore)

    // Dagger Hilt
    kaptAndroidTest(libs.dagger)
    // Hilt dependencies
    implementation(libs.daggerHilt)
    kapt(libs.daggerHiltCompiler)

    implementation(libs.paging3)
    implementation(libs.webSocket)

    implementation(libs.geoHash)
}