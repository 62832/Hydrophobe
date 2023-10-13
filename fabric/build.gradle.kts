repositories {
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
        content {
            includeGroup("com.terraformersmc")
        }
    }
}

dependencies {
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)

    modImplementation(libs.cloth.fabric)
    modImplementation(libs.modmenu)

    modRuntimeOnly(libs.jei.fabric)
    modRuntimeOnly(libs.jade.fabric)
}

tasks {
    processResources {
        filesMatching("fabric.mod.json") {
            val commonProps: Map<String, *> by extra
            expand(commonProps)
        }
    }
}
