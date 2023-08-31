package net.kigawa.fns.backend

import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.kigawa.fns.backend.plugins.configureRouting

fun main(args: Array<String>) {
  EngineMain.main(args)
}

fun Application.module() {
  configureRouting()
}
