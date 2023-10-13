pluginManagement {
    repositories {
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.architectury.dev/") }
        maven { url = uri("https://maven.minecraftforge.net/") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("loom", "dev.architectury.loom").version("1.3-SNAPSHOT")
            plugin("architectury", "architectury-plugin").version("3.4-SNAPSHOT")
            plugin("vineflower", "io.github.juuxel.loom-vineflower").version("1.11.0")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("spotless", "com.diffplug.spotless").version("6.20.0")

            val minecraftVersion = "1.19.2"
            version("minecraft", minecraftVersion)
            library("minecraft", "com.mojang", "minecraft").versionRef("minecraft")

            library("fabric-loader", "net.fabricmc", "fabric-loader").version("0.14.21")
            library("fabric-api", "net.fabricmc.fabric-api", "fabric-api").version("0.75.1+$minecraftVersion")
            library("forge", "net.minecraftforge", "forge").version("$minecraftVersion-43.3.0")

            version("cloth-config", "8.3.103")
            library("cloth-fabric", "me.shedaniel.cloth", "cloth-config-fabric").versionRef("cloth-config")
            library("cloth-forge", "me.shedaniel.cloth", "cloth-config-forge").versionRef("cloth-config")
            library("modmenu", "com.terraformersmc", "modmenu").version("4.1.2")

            version("jei", "11.6.0.1018")
            library("jei-fabric", "mezz.jei", "jei-$minecraftVersion-fabric").versionRef("jei")
            library("jei-forge", "mezz.jei", "jei-$minecraftVersion-forge").versionRef("jei")

            library("jade-fabric", "curse.maven", "jade-324717").version("4328558")
            library("jade-forge", "curse.maven", "jade-324717").version("4719987")
        }
    }
}

include("common")

for (platform in providers.gradleProperty("enabledPlatforms").get().split(',')) {
    include(platform)
}

val modName: String by settings
rootProject.name = modName
