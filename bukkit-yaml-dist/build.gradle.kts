plugins {
    id("storage.publishing-conventions")
    id("storage.spigot-conventions")
}

dependencies {
    api(project(":lightweight-storage-api-codec"))
    compileOnly(libs.spigot)
}