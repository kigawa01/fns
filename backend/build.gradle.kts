import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("io.ktor.plugin") version "2.3.3"
}

val ktorVersion = project.property("ktor.version") as String
val kotlinVersion = project.property("kotlin.version") as String
val logbackVersion= project.property("logback.version") as String
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


dependencies {
  implementation(project(":share"))
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("io.ktor:ktor-server-config-yaml")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  testImplementation("io.ktor:ktor-server-tests-jvm")
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
