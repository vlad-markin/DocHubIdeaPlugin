pluginManagement {

    repositories {
        repositories {
            maven {
                url = uri("https://plugins.gradle.org/m2/")
            }
        }
    }

    val kotlinVersion: String by settings
    val gradleIntellijPluginVersion: String by settings
    val grammarKitVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.intellij") version gradleIntellijPluginVersion
        id("org.jetbrains.grammarkit") version grammarKitVersion
    }
}

rootProject.name = "IDEAPlugin"