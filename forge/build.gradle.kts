loom {
    forge {
        mixinConfig("${project.property("modId")}.mixins.json")
    }
}

dependencies {
    forge(libs.forge)
    modImplementation(libs.cloth.forge)

    modRuntimeOnly(libs.jei.forge) { isTransitive = false }
    modRuntimeOnly(libs.jade.forge)
}

tasks.processResources {
    val commonProps: Map<String, *> by extra
    val forgeProps = mapOf(
            "loaderVersion" to libs.forge.get().version!!.substringAfter('-').substringBefore('.')
    )

    inputs.properties(commonProps)
    inputs.properties(forgeProps)

    filesMatching("META-INF/mods.toml") {
        expand(commonProps + forgeProps)
    }
}
