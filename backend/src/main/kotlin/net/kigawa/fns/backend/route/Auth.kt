package net.kigawa.fns.backend.route

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Auth {
  fun route(route: Route) {
    route.route("/auth") {
      post("/login") {
        val loginInfo = call.receive<LoginInfo>()
      }
    }
  }
}
