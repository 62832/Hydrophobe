pluginManagement {
    plugins {
        id("net.neoforged.moddev") version "0.1.112"
        id("net.neoforged.moddev.repositories") version "0.1.112"
        id("com.diffplug.spotless") version "6.25.0"
    }
}

plugins {
    id("net.neoforged.moddev.repositories")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
