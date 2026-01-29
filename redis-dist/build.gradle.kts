plugins {
    id("storage.publishing-conventions")
}

dependencies {
    api(project(":lightweight-storage-api"))
    api(libs.jedis)
}