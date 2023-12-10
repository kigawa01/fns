plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.9.21"
}

group = project.property("project.group") as String
version = project.property("project.version") as String

kotlin {
  jvm("jvm") {}
  js(IR) {
    browser {}
  }
  sourceSets {
    val commonMain by getting
    val commonTest by getting
    val jsMain by getting
    val jsTest by getting
    val jvmMain by getting {
      dependencies {
        implementation("net.kigawa.kutil:log:2.0")
      }

    }
    val jvmTest by getting
  }
}
repositories {
  mavenCentral()
}

dependencies {
  commonMainImplementation(Depends.Ktor.serializationKotlinxJson)
  commonMainImplementation("org.jetbrains.kotlin:kotlin-stdlib")
  commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
