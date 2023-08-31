plugins {
  kotlin("multiplatform") version "1.8.22" apply false
}

group = project.property("project.group") as String
version = project.property("project.version") as String

allprojects {
  repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
  }
}