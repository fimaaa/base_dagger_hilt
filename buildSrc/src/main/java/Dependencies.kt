object Modules {
    const val common = ":common"
}

object Release {
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 1
    const val versionCode: Int = versionMajor*10000 + versionMinor*100 + versionPatch
    const val versionAppName: String = "$versionMajor.$versionMinor.$versionPatch"
    const val minSDK = 21
    const val maxSDK = 32

    const val appName = "BaseDaggerHilt"
}

object Versions {
    const val gradle = "4.2.2"
    const val gradlePlugin = "1.7.0"
    const val googleService = "4.3.13"
    const val firebaseCrashlytics = "2.9.1"
    const val firebaseBOM = "30.4.0"
    const val kotlinVersion = "1.6.21"
    const val navigation = "2.5.1"
    const val sonarQube = "2.8"
    const val jacoco = "0.8.7"

    const val daggerVersion = "2.43.2"
    const val appCompatVersion = "1.5.0"
    const val constraintLayoutVersion = "2.1.4"
    const val materialVersion = "1.7.0-alpha01"
    const val espressoVersion = "3.4.0"
    const val glideVersion = "4.12.0"
    const val glideVector = "v2.0.0"
    const val junitVersion = "4.13.2"
    const val ktxVersion = "1.8.0"
    const val pagingVersion = "3.1.1"
    const val retrofitVersion = "2.9.0"
    const val retrofitCoroutines = "0.9.2"
    const val testExtJunitVersion = "1.1.3"
    const val leakCanaryVersion = "2.7"
    const val chuckerVersion = "3.5.2"
    const val gooleAuth = "20.3.0"
    const val googleAds = "21.2.0"
    const val gson = "2.8.9"
    const val googleAR = "1.31.0"
    const val roomVersion = "2.4.3"

    const val test = "1.4.0"
    const val truth = "1.0.1"
    const val mockito = "2.23.4"
    const val coroutinesTest = "1.5.2"
    const val archCore = "2.1.0"

    const val recyclerView = "1.2.1"
    const val anko = "0.10.8"

    const val rxAndroid = "2.1.1"
    const val rxJava = "2.2.10"
    const val rxBinding = "2.2.0"

    const val okhttpLogging = "5.0.0-alpha.2"
    const val lifeCycle = "2.5.1"
    const val lifeCycleExt = "2.2.0"

    const val localeHelper = "1.1.4"

    const val skeletonLayout = "4.0.0"
    const val progressButton = "2.1.0"

    const val multiDex = "2.0.1"

    const val compose = "1.2.1"
    const val composeActivity = "1.5.1"
    const val composeViewModel = "2.5.1"
}

object LibraryProject {
    const val gradle =  "com.android.tools.build:gradle:${Versions.gradle}"
    const val gradlePlugin =  "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.gradlePlugin}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerVersion}"
    const val sonarQube = "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.sonarQube}"
    const val googleService = "com.google.gms:google-services:${Versions.googleService}"

    // Add the Crashlytics Gradle plugin
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlytics}"
    const val jacoco = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
}

object LibraryAndroid {
    const val kotlinX = "androidx.core:core-ktx:$${Versions.ktxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val googleMaterial = "com.google.android.material:material:${Versions.materialVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    const val ankoCommon = "org.jetbrains.anko:anko-common:${Versions.anko}"
    const val googleAR = "com.google.ar:core:${Versions.googleAR}"

    // Check Leak Memory
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitRX = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val retrofitCoroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}"

    // Room
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomTesting = "androidx.room:room-testing:${Versions.roomVersion}"

    // Lifecycle
    const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
    const val lifeCycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
    const val lifeCycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExt}"

    // DataBinding
    const val dataBindingCompiler = "com.android.databinding:compiler:${Versions.gradle}" // 4.2.1
    const val rxBinding = "com.jakewharton.rxbinding2:rxbinding:${Versions.rxBinding}"

    // Navigation Component
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" // 2.5.1
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" // 2.5.1

    // ...with Kotlin.
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerVersion}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerVersion}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    const val glideToVector = "com.github.corouteam:GlideToVectorYou:${Versions.glideVector}"

    // Change Language
    const val localeHelper = "com.zeugmasolutions.localehelper:locale-helper-android:${Versions.localeHelper}"

    // Paging 3
    const val paging3 = "androidx.paging:paging-runtime-ktx:${Versions.pagingVersion}"

    //HTTP Inspector
    const val chuckerActive = "com.github.chuckerteam.chucker:library:${Versions.chuckerVersion}"
    const val chuckerDisable = "com.github.chuckerteam.chucker:library-no-op:${Versions.chuckerVersion}"

    // Google
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val googleAuth = "com.google.android.gms:play-services-auth:${Versions.gooleAuth}"
    // Google Ads
    const val googleAds = "com.google.android.gms:play-services-ads:${Versions.googleAds}"

    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    const val firebaseBOM = "com.google.firebase:firebase-bom:${Versions.firebaseBOM}"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"

    // Style
    const val skeletonLayout = "com.faltenreich:skeletonlayout:${Versions.skeletonLayout}"
    const val progressButton = "com.github.razir.progressbutton:progressbutton:${Versions.progressButton}"

    /**
    Compose
     **/
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    // Tooling support (Previews, etc.)
    const val composeUITool = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    // Material Design
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    // Material design icons
    const val composeMaterialIconCore = "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val composeMaterialIconExt = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    // Integration with observables
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val composeRXJava = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose}"
    // Integration with activities
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    // Integration with ViewModels
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
    /**
    Compose
     **/

    // Multidex
    const val multiDex = "androidx.multidex:multidex:${Versions.multiDex}"
}

object LibraryAndroidTesting {
    const val jUnit ="junit:junit:${Versions.junitVersion}"
    const val jUnitTest = "androidx.test.ext:junit:${Versions.testExtJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val testRunner =  "androidx.test:runner:${Versions.test}"
    const val testRules = "androidx.test:rules:${Versions.test}"

    // UI Tests
    const val composeJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"

    // For Robolectric tests.
    // Dagger Hilt
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerHiltTesting = "com.google.dagger:hilt-android-testing:${Versions.daggerVersion}"

    const val truth = "com.google.truth:truth:${Versions.truth}"

    const val mockitoCore =  "org.mockito:mockito-core:${Versions.mockito}"

    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"

    const val archCore = "androidx.arch.core:core-testing:${Versions.archCore}"
}