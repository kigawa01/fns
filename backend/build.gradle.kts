plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("jvm")
  id("io.ktor.plugin") version "2.3.3"
  kotlin("plugin.serialization") version "1.9.20-RC"
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
fun DependencyHandler.ktor(
  id: String, version: String = Depends.Ktor.version
): Dependency? {
  return implementation(Depends.Ktor.strId(id, version))
}

fun DependencyHandler.ktorLib(id: String, version: String = Depends.KtorLib.version): Dependency? {
  return implementation(Depends.KtorLib.strId(id, version))
}

dependencies {
  implementation(project(":share"))
  ktor("ktor-server-auth-jvm")
  ktor("ktor-server-core-jvm")
  ktor("ktor-server-auth-jwt-jvm")
  ktor("ktor-server-core-jvm")
  ktor("ktor-server-netty-jvm")
  ktor("ktor-server-config-yaml")
  ktor("ktor-server-resources")
  ktor("ktor-server-content-negotiation")
  ktorLib("ktor-auth")
  ktorLib("ktor-auth-jwt")
  ktorLib("ktor-jackson")
  implementation(Depends.Ktor.serializationKotlinxJson)
  implementation(Depends.Exposed.strId("exposed-core"))
  implementation(Depends.Exposed.strId("exposed-dao"))
  implementation(Depends.Exposed.strId("exposed-jdbc"))
  implementation(Depends.Exposed.strId("exposed-jodatime"))
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("org.mindrot:jbcrypt:0.4")
  implementation("com.mysql:mysql-connector-j:8.1.0")
  implementation("net.kigawa.kutil:kutil-unit:4.4.0")

  testImplementation("io.ktor:ktor-server-tests-jvm:2.3.5")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
