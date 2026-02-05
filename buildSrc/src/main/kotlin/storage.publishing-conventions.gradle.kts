plugins {
    id("storage.common-conventions")
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name
            from(components["java"])
        }
    }
}