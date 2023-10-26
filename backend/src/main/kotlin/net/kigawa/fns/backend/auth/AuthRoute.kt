package net.kigawa.fns.backend.auth

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.table.User
import net.kigawa.fns.backend.util.KutilKtor
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

@Kunit
class AuthRoute(
  private val tokenManager: TokenManager,
) {
  fun route(route: Route) {
    route.route("/auth") {
      post("/login") {
        val loginInfo = try {
          call.receive<LoginInfo>()
        } catch (e: BadRequestException) {
          KutilKtor.respondErr(call, ErrID.BadRequest)
          return@post
        }

        if (loginInfo.username == "") return@post KutilKtor.respondErr(call, ErrID.UsernameIsEmpty)
        if (loginInfo.password == "") return@post KutilKtor.respondErr(call, ErrID.PasswordIsEmpty)


        val result = transaction {
          User
            .select { User.name eq loginInfo.username }
            .singleOrNull()
        } ?: return@post KutilKtor.respondErr(call, ErrID.UserNotExits)

        if (!BCrypt.checkpw(loginInfo.password, result[User.password]))
          return@post KutilKtor.respondErr(call, ErrID.InvalidPassword)


        val id = result[User.id].value
        return@post call.respond(
          Tokens(
            tokenManager.createToken(id, TokenType.ACCESS),
            tokenManager.createToken(id, TokenType.REFRESH),
          )
        )
      }
      post("/register"){}
    }
  }
}
