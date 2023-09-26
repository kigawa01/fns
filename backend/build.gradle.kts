import dependencies.ProjectConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("jvm")
  id("io.ktor.plugin") version "2.3.3"
}

val kotlinVersion = project.property("kotlin.version") as String
val logbackVersion = project.property("logback.version") as String
group = project.property("project.group") as String
version = project.property("project.version") as String

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

application {
  mainClass.set("net.kigawa.fns.backend.FnsApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
fun DependencyHandler.ktor(id: String, version: String = ProjectConfig.Ktor.VERSION): Dependency? {
  return implementation("io.ktor:$id:$version")
}

fun DependencyHandler.ktorLib(id: String, version: String = ProjectConfig.Ktor.LIB_VERSION): Dependency? {
  return implementation("io.ktor:$id:$version")
}

dependencies {
  implementation(project(":share"))
  ktor("ktor-server-auth-jvm")
  ktor("ktor-server-core-jvm")
  ktor("ktor-server-auth-jwt-jvm")
  ktor("ktor-server-core-jvm")
  ktor("ktor-server-netty-jvm")
  ktor("ktor-server-config-yaml")
  ktorLib("ktor-auth")
  ktorLib("ktor-auth-jwt")
  ktorLib("ktor-jackson")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("net.kigawa.kutil:kutil-unit:4.4.0")

  testImplementation("io.ktor:ktor-server-tests-jvm:2.3.4")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
