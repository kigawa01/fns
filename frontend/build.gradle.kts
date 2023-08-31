plugins {
  kotlin("js")
}

group = project.property("project.group") as String
version = project.property("project.version") as String
val reactVersion = project.property("react.version") as String
val reactRouterVersion = project.property("react-router.version") as String
val emotionVersion = project.property("emotion.version") as String
val kotlinWrappersPrefix = project.property("kotlin.wrappers.prefix") as String

val kotlinWrapperReactVersion = "$reactVersion$kotlinWrappersPrefix"
val kotlinWrapperReactRouterVersion = "$reactRouterVersion$kotlinWrappersPrefix"
val kotlinWrapperEmotionVersion = "$emotionVersion$kotlinWrappersPrefix"

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinWrapperReactVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinWrapperReactVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:$kotlinWrapperReactRouterVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:$kotlinWrapperEmotionVersion")

  implementation(npm("postcss", "8.2.6"))
  implementation(npm("postcss-loader", "4.2.0"))
}

kotlin {
  js(IR) {
    browser {
      runTask {
        devServer?.apply {
          port = 8081
          proxy = mutableMapOf(Pair("/api", "http://127.0.0.1:8080"))
          static !!.add("./resources/public")
        }
      }
    }
    binaries.executable()
  }
}
