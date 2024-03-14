@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.sonarqube")
    id("jacoco")
}

// Apply external build scripts
apply {
    from("${project.rootDir}/library/common/android_common.gradle")
    from("${project.rootDir}/library/common/android_core_dependencies.gradle")
    from("${project.rootDir}/library/common/jacoco.gradle")
}

android {
    namespace = "com.baseapp.daggerhilt"
    packaging  {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module"
            )
        }
    }

    defaultConfig {
        applicationId = "com.baseapp.daggerhilt"
        versionCode = Release.VERSION_CODE
        versionName = Release.VERSION_APP_NAME
        @Suppress("UnstableApiUsage")
        externalNativeBuild {
            cmake {
                cppFlags
            }
        }
        signingConfig = signingConfigs.getByName("debug")
        applicationVariants.all {
            outputs.forEach { output ->
                if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                    val outputFileName = "base-app-dagger-hilt-${this.versionName}.${output.outputFile.extension}"
                    output.outputFileName = outputFileName
                }
            }
        }
    }

    buildTypes {
        getByName("release") {
            resValue("string", "version_name", Release.VERSION_APP_NAME)
            resValue("string", "app_name", Release.APP_NAME)
            buildConfigField("String", "VARIANT", "\"release\"")
            isMinifyEnabled = true
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        named("preprod") {
            resValue("string", "version_name", "${Release.VERSION_APP_NAME}-preprod")
            resValue("string", "app_name", "${Release.APP_NAME}-preprod")
            buildConfigField("String", "VARIANT", "\"preprod\"")
            versionNameSuffix = "-preprod"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        named("qa") {
            resValue("string", "version_name", "${Release.VERSION_APP_NAME}-qa")
            resValue("string", "app_name", "${Release.APP_NAME}-qa")
            buildConfigField("String", "VARIANT", "\"qa\"")
            isMinifyEnabled = true
            isShrinkResources = true
            versionNameSuffix = "-qa"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        named("debug") {
            resValue("string", "version_name", "${Release.VERSION_APP_NAME}-dev")
            resValue("string", "app_name", "${Release.APP_NAME}-dev")
            buildConfigField("String", "VARIANT", "\"development\"")
            versionNameSuffix = "-dev"
        }
        named("localhost") {
            resValue("string", "version_name", "${Release.VERSION_APP_NAME}-local")
            resValue("string", "app_name", "${Release.APP_NAME}-local")
            buildConfigField("String", "VARIANT", "\"development\"")
            versionNameSuffix = "-local"
        }
    }

//    testOptions {
//        unitTests.returnDefaultValues = true
//        unitTests.all {
//            jacoco {
//                includeNoLocationClasses = true
//            }
//        }
//    }
}

dependencies {
    implementation(project(Modules.NAVIGATION))
    implementation(project(Modules.REPOSITORY))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.LOCAL))

    implementation(project(Modules.Library.COMMON))

    implementation(project(Modules.Model.COMMON))

    implementation(project(Modules.Feature.MAIN))
    implementation(project(Modules.Feature.SPLASHSCREEN))
    implementation(project(Modules.Feature.AUTH))
    implementation(project(Modules.Feature.ADMIN))

    implementation(project(Modules.Feature.CRYPTO))
    implementation(project(Modules.Feature.EMPLOYEE))
    implementation(project(Modules.Feature.FIREBASE_TEST))

    testImplementation(project(Modules.Library.COMMON))
    androidTestImplementation(project(Modules.Library.COMMON))

    // Networking
    // Retrofit + GSON
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.retrofitRX)
    implementation(libs.retrofitConverterMoshi)
    implementation(libs.retrofitCoroutines)

    implementation(platform(libs.okhttpPlatform))
    implementation(libs.okHttp)
    implementation(libs.okhttpLogging)

    // Room
    implementation(libs.roomRuntime)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)
    implementation(libs.room)
    testImplementation(libs.roomTesting)

    //HTTP Inspector
    "localhostImplementation"(libs.chuckerActive)
    debugImplementation(libs.chuckerActive)
    "qaImplementation"(libs.chuckerActive)
    "preprodImplementation"(libs.chuckerActive)
    releaseImplementation(libs.chuckerDisable)

    // Google
    implementation(libs.gson)
    implementation(libs.googleAuth)
    // Google Ads
    implementation(libs.googleAds)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebaseBOM))
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebaseCrashlytics)
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.firebaseNotification)
    implementation(libs.firebaseDatabase)

    implementation(libs.customCrash)
}

kapt {
    correctErrorTypes = true
}