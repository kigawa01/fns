plugins {
  id("net.kigawa.fns.java-conventions")
  kotlin("jvm")
  id("io.ktor.plugin") version "2.3.3"
  kotlin("plugin.serialization") version "1.9.21"
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
  id: String, version: String = Depends.Ktor.version,
): Dependency? {
  return implementation(Depends.Ktor.strId(id, version))
}

fun DependencyHandler.ktorLib(id: String, version: String = Depends.KtorLib.version): Dependency? {
  return implementation(Depends.KtorLib.strId(id, version))
}

dependencies {
  implementation(project(":share"))
  implementation("io.ktor:ktor-server-auto-head-response-jvm:2.3.5")
  implementation("io.ktor:ktor-server-core-jvm:2.3.5")
  implementation("io.ktor:ktor-server-host-common-jvm:2.3.5")
  implementation("io.ktor:ktor-server-status-pages-jvm:2.3.5")
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
  implementation(Depends.Ktor.strId("ktor-server-request-validation"))
  implementation(Depends.Ktor.serializationKotlinxJson)
  implementation(Depends.Exposed.strId("exposed-core"))
  implementation(Depends.Exposed.strId("exposed-dao"))
  implementation(Depends.Exposed.strId("exposed-jdbc"))
  implementation(Depends.Exposed.strId("exposed-jodatime"))
  implementation("org.mindrot:jbcrypt:0.4")
  implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2")
//  implementation("com.mysql:mysql-connector-j:8.1.0")
  implementation("net.kigawa.kutil:kutil-unit:4.4.0")
  // https://mvnrepository.com/artifact/org.apache.commons/commons-imaging
  implementation("org.apache.commons:commons-imaging:1.0-alpha3")
// https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
  implementation("org.slf4j:slf4j-simple:2.0.9")
// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
  implementation("org.slf4j:slf4j-api:2.0.9")


  testImplementation("io.ktor:ktor-server-tests-jvm:2.3.5")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
application {
  mainClass.set("net.kigawa.fns.backend.FnsApplication")
}
tasks.withType<Test> {
  useJUnitPlatform()
}