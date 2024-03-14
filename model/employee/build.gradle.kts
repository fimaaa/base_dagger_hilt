plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
//
//android {
//    namespace = "com.baseapp.crypt"
//    compileSdk = 33
//
//    defaultConfig {
//        minSdk = 24
//        targetSdk = 33
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}

apply("${project.rootDir}/library/common/android_common.gradle")
//apply("${project.rootDir}/common/android_core_dependencies.gradle")
android {
    namespace = "com.model.employee"
}

dependencies {
    implementation(libs.gson)

    implementation(libs.roomRuntime)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)
    implementation(libs.room)
    testImplementation(libs.roomTesting)

//    implementation(libs.roomCommon)
}