architectury {
    val platforms = property("enabledPlatforms").toString().split(',')
    println("Platforms: $platforms")
    common(platforms)
}

dependencies {
    modImplementation(libs.fabric.loader)
    modCompileOnly(libs.cloth.fabric)
}
