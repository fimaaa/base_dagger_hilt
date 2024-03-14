pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "Base Dagger"
include ("app")
include ("navigation")
include ("network")
include ("local")
include ("repository")

include ("library:common")
include ("library:unittest")

include ("model:common")
include ("model:employee")
include ("model:crypto")

include ("feature:main")
include ("feature:auth")
include ("feature:admin")
include ("feature:splashscreen")
include ("feature:employee")
include ("feature:firebasetest")
include ("feature:crypto")

plugins {
    // See https://jmfayard.github.io/refreshVersions
    // To Rn ./gradlew refreshVersions
    id("de.fayard.refreshVersions") version "0.60.3"
}

