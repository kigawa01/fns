package net.kigawa.fns.backend.auth

import com.auth0.jwt.JWT
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.table.User
import net.kigawa.fns.backend.util.KutilKtor
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Kunit
class AuthRoute(
) {
  fun route(route: Route) {
    route.route("/auth") {
      post("/login") {
        val loginInfo = call.receive<LoginInfo>()

        val result = transaction {
          User.select { User.name eq loginInfo.username }.singleOrNull()
        }
        if (result == null) {
          KutilKtor.unauthorized(call, "user[${loginInfo.username}] is not exists")
          return@post
        }
        if (!BCrypt.checkpw(loginInfo.password, result[User.password])) {
          KutilKtor.unauthorized(call, "password is invalid")
          return@post
        }
      }
    }
  }
}
