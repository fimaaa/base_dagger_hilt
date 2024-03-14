plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply("${project.rootDir}/library/common/android_common.gradle")
apply("${project.rootDir}/library/common/android_core_dependencies.gradle")

android {
    namespace = "com.stockbit.crypto"
}

dependencies {
    implementation(project(Modules.Library.COMMON))
    implementation(project(Modules.NAVIGATION))
    implementation(project(Modules.Model.COMMON))
    implementation(project(Modules.Model.CRYPTO))
    implementation(project(Modules.REPOSITORY))

    implementation(libs.webSocket)
    implementation(libs.kotlinx.serialization)
}