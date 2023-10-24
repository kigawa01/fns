package net.kigawa.fns.backend.route

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Routing(private val auth: Auth) {

  fun configureRouting(application: Application) {
    application.routing {
      get("/") {
        call.respondText("Hello World!")
      }
      route("/api") {
        auth.route(this)
      }
    }
  }
}
