plugins {
    id("storage.common-conventions")
}

repositories {
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public/")
    }

    maven {
        name = "BungeeCord"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}