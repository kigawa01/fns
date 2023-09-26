package net.kigawa.fns.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Routing {

  fun configureRouting(application: Application) {
    application.routing {
      get("/") {
        call.respondText("Hello World!")
      }
      get("/api") {
        call.respondText("ap")
      }
    }
  }
}
