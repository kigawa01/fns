package net.kigawa.fns.backend.service

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.auth.AuthRoute
import net.kigawa.fns.backend.post.PostRoute
import net.kigawa.fns.backend.user.UserRoute
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Routing(
  private val auth: AuthRoute,
  private val user: UserRoute,
  private val post: PostRoute,
) {

  fun configureRouting(application: Application) {
    application.routing {
      get("/") {
        call.respondText("Hello World!")
      }
      route("/api") {
        auth.route(this)
        user.route(this)
        post.route(this)
      }
    }
  }
}
