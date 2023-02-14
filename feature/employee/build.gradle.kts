plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply("${project.rootDir}/common/android_common.gradle")
apply("${project.rootDir}/common/android_core_dependencies.gradle")

android {
    namespace = "com.feature.employee"
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.navigation))
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.modelEmployee))
    implementation(project(Modules.repository))
}