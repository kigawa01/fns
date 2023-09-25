plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("multiplatform")
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
}
