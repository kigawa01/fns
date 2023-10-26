package net.kigawa.fns.backend

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import net.kigawa.fns.backend.service.DatabaseManager
import net.kigawa.fns.backend.service.Routing
import net.kigawa.fns.backend.auth.TokenManager
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Modules(
  private val routes: Routing,
  private val databaseManager: DatabaseManager,
  private val tokenManager: TokenManager,
) {
  fun loadModule(application: Application) {
    application.install(Resources)
    application.install(ContentNegotiation) {
      json()
    }

    tokenManager.loadModule(application)
    routes.configureRouting(application)
    databaseManager.init()
  }
}

fun Application.loadModule() {
  FnsApplication.app.loadModule(this)
}