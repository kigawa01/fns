plugins {
  kotlin("js") version "1.8.22"
}

group = "net.kigawa"
version = "0.0.1"

repositories {
  mavenCentral()
}

dependencies {
}

kotlin {
  js(IR) {
    browser {
      runTask {
        devServer?.apply {
          port = 8081
          proxy = mutableMapOf(Pair("/api", "http://127.0.0.1:8080/api"))
        }
      }
    }
    binaries.executable()
  }
}
