# 📦 Lightweight-Storage

**Lightweight-Storage** is a lightweight storage system inspired by legacy storage libraries.
This project is organized as a multi-module Gradle project with several specific distributions (e.g., for *Bukkit*, *Redis*, and *Mongo*), aiming to provide a simple and flexible alternative for handling storage in Java.

---

## 🧱 Project Structure

The repository is divided into multiple independent modules:

```
lightweight-storage
├── api                # Core API
├── api-codec          # Codec utilities for the API
├── bukkit-yaml-dist   # Bukkit YAML distribution
├── mongo-legacy-dist  # Legacy MongoDB distribution
├── redis-dist         # Redis distribution
├── buildSrc           # Shared build logic
├── gradle             # Gradle scripts and configuration
├── gradlew*           # Gradle wrapper
├── settings.gradle.kts
└── gradle.properties
```

---

## 🚀 Features

* Modular — each distribution can be used independently.
* Written in Java/Kotlin with support for multiple storage backends.
* Compatible with projects using Gradle as the build system.
* Ideal for projects requiring lightweight storage without heavy dependencies.

> 💡 All modules are built from the `develop` branch; no official releases yet.

---

## 🧠 Requirements

To build and use this project, you need:

* **Java 8 or higher**
* **Gradle 8.x or higher**

---

## ⚙️ Building the Project

Clone the repository and build with Gradle:

```bash
git clone https://github.com/alexissdev/lightweight-storage.git
cd lightweight-storage
./gradlew build

./gradlew publishToMavenLocal /*  optional from publish all modules * /
```

This will compile all modules and generate the corresponding JARs.

---

## 📦 Using the Modules via Maven Local

Each module can be consumed as a Gradle dependency **after publishing it to your local Maven repository**.

### Steps:

1. Build and publish the module locally:

```bash
./gradlew :<module-name>:publishToMavenLocal
```

> Replace `<module-name>` with the desired module, e.g., `api`, `bukkit-yaml-dist`, etc.

1. Add `mavenLocal()` to your consuming project’s `repositories` block:

```kotlin
repositories {
    mavenLocal()
    mavenCentral()
}
```

1. Add the dependency in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.alexissdev:<module-name>:VERSION")
}
```

> Replace `<module-name>` and `VERSION` with the module name and version you published locally.

---

## 🧪 Running Tests

If the modules include tests, run them with:

```bash
./gradlew test
```

---

## 🛠 Contributing

Contributions are welcome!

1. Fork the repository.
2. Create a branch for your feature or fix.
3. Submit a Pull Request from your fork to the `develop` branch.

---
