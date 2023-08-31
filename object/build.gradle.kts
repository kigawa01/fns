plugins {
  kotlin("multiplatform") version "1.8.22"
}

group = project.property("project.group") as String
version = project.property("project.version") as String

kotlin{
  jvm {
  }
}
repositories {
}

dependencies {
}
