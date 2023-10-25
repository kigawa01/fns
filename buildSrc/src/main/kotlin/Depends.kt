object Depends {
  object KtorLib : DependenciesBase("1.6.8", "io.ktor")
  object Exposed : DependenciesBase("0.44.0", "org.jetbrains.exposed")
  object Ktor : DependenciesBase("2.3.3", "io.ktor") {
    val serializationKotlinxJson = strId("ktor-serialization-kotlinx-json")
  }
}

abstract class DependenciesBase(val version: String, val group: String) {
  fun strId(id: String, version: String = this.version, group: String = this.group) = "$group:$id:$version"
}