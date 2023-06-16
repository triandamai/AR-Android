@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://jitpack.io")
    }
    versionCatalogs {
        create("libs"){
            from(files("core-ui/libs.versions.toml"))
        }
    }

}
rootProject.name = "Hilwa"
include(":androidApp")
include(":core-ui")
include(":core-annotation")

project(":core-ui").projectDir = File("core-ui/ui")
project(":core-annotation").projectDir = File("core-ui/annotation")