package net.kigawa.fns.backend.auth

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.table.User
import net.kigawa.fns.backend.util.KutilKtor
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

@Kunit
class AuthRoute {
  fun route(route: Route) {
    route.route("/auth") {
      post("/login") {
        val loginInfo = call.receive<LoginInfo>()

        val result = transaction {
          User.select { User.name eq loginInfo.username }.singleOrNull()
        }
        if (result == null) {
          KutilKtor.resourceNotFound(call, "user[${loginInfo.username}] is not exists")
          return@post
        }

//        AuthResponse(
//          JWT.create()
//            .withAudience(audience)
//            .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC))) // 有効期限
//            .withClaim(userId, id)
//            .withIssuer(issuer)
//            .sign(algorithm)
      }
    }
  }
}
