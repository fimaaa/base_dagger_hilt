plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${project.rootDir}/common/android_common.gradle")
apply(from = "${project.rootDir}/common/android_core_dependencies.gradle")

android {
    packagingOptions {
        resources {
            excludes += setOf(
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

        consumerProguardFiles("consumer-rules.pro")
    }

    namespace = "com.basedagger.common"
}

dependencies {
    implementation(project(Modules.modelCommon))
    implementation(project(Modules.navigation))

    implementation(LibraryAndroid.retrofit)
}
