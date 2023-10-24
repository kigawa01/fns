plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.9.20-RC"
}

group = project.property("project.group") as String
version = project.property("project.version") as String

kotlin {
  jvm {
  }
  js(IR) {
    browser {}
  }
}
repositories {
  mavenCentral()
}

dependencies {
  commonMainImplementation(Depends.Ktor.serializationKotlinxJson)
}
