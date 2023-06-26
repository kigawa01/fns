plugins {
    kotlin("js")
}

group = "net.kigawa"
version = "0.0.1"
val reactVersion = project.property("react.version") as String
val kotlinWrappersPrefix = project.property("kotlin.wrappers.prefix") as String
val kotlinWrapperVersion = reactVersion + kotlinWrappersPrefix

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinWrapperVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinWrapperVersion")
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
