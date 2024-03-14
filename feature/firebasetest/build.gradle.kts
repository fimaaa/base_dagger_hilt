plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply("${project.rootDir}/library/common/android_common.gradle")
apply("${project.rootDir}/library/common/android_core_dependencies.gradle")

android {
    namespace = "com.feature.baseapp.firebasetest"
}

dependencies {
    implementation(project(Modules.NAVIGATION))

    implementation(project(Modules.Library.COMMON))
    
    implementation(project(Modules.Model.COMMON))

    implementation(platform(libs.firebaseBOM))
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebaseCrashlytics)
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.firebaseNotification)
    implementation(libs.firebaseInAppMessage)
    implementation(libs.firebaseDatabase)

    implementation(libs.googleAuth)
}