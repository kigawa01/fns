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
//  https://central.sonatype.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-react
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.655")
//  https://central.sonatype.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-react-dom
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.655")
//  https://central.sonatype.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-react-router-dom
  implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.20.1-pre.655")
//  https://central.sonatype.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-emotion
  implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.1-pre.655")
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
