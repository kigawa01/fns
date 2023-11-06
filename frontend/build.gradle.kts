plugins {
  id("net.kigawa.fns.java-conventions")
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
  implementation(project(":share"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinWrapperReactVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinWrapperReactVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:$kotlinWrapperReactRouterVersion")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:$kotlinWrapperEmotionVersion")
  // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  implementation(Depends.Ktor.serializationKotlinxJson)


  implementation(npm("postcss", "8.2.6"))
  implementation(npm("postcss-loader", "4.2.0"))
}

kotlin {
  js(IR) {
    browser {
      runTask(Action {
        devServer?.apply {
          port = 8081
          proxy = mutableMapOf(Pair("/api", "http://127.0.0.1:8080"))
          static!!.add(projectDir.resolve("src/main/resources/public").path)
        }
      })
    }
    binaries.executable()
  }
}
