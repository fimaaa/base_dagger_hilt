plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply("${project.rootDir}/common/android_common.gradle")
apply("${project.rootDir}/common/android_core_dependencies.gradle")

android {
    namespace = "com.feature.baseapp.firebasetest"
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.navigation))

    implementation(platform(LibraryAndroid.firebaseBOM))
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(LibraryAndroid.firebaseCrashlytics)
    implementation(LibraryAndroid.firebaseAnalytics)
    implementation(LibraryAndroid.firebaseAuth)
    implementation(LibraryAndroid.firebaseNotification)
    implementation(LibraryAndroid.firebaseInAppMessage)
    implementation(LibraryAndroid.firebaseDatabase)

    implementation(LibraryAndroid.googleAuth)
}