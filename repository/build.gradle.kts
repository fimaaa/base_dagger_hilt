plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply("${project.rootDir}/common/android_common.gradle")
android {
    namespace = "com.baseapp.repository"
}

dependencies {

    implementation(project(Modules.modelCommon))
    implementation(project(Modules.modelCrypto))
    implementation(project(Modules.modelEmployee))
    implementation(project(Modules.network))
    implementation(project(Modules.local))

    // Dagger Hilt
    kaptAndroidTest(LibraryAndroidTesting.dagger)
    // Hilt dependencies
    implementation(LibraryAndroid.daggerHilt)
    kapt(LibraryAndroid.daggerHiltCompiler)

    implementation(LibraryAndroid.paging3)
    implementation(LibraryAndroid.webSocket)
}