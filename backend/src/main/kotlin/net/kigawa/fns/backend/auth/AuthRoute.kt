package net.kigawa.fns.backend.auth

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.table.User
import net.kigawa.fns.backend.util.KutilKtor
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

@Kunit
class AuthRoute(
  private val tokenManager: TokenManager,
) {
  fun route(route: Route) {
    route.route("/auth") {
      postLogin()
      postRegister()
    }
  }

  private fun Route.postRegister() = post("/register") {
    val userInfo = KutilKtor.tryReceive<UserInfo>(call) ?: return@post

    if (userInfo.username == "") return@post KutilKtor.respondErr(call, ErrID.UsernameIsEmpty)
    if (userInfo.password == "") return@post KutilKtor.respondErr(call, ErrID.PasswordIsEmpty)

    if (transaction { User.select { User.name eq userInfo.username }.count() } != 0L)
      return@post KutilKtor.respondErr(call, ErrID.UserAlreadyExists)

    val id = transaction {
      User.insertAndGetId {
        it[name] = userInfo.username
        it[password] = BCrypt.hashpw(userInfo.password, BCrypt.gensalt())
      }
    }.value
    return@post call.respond(
      Tokens(
        tokenManager.createToken(id, TokenType.ACCESS),
        tokenManager.createToken(id, TokenType.REFRESH),
      )
    )
  }

  private fun Route.postLogin() = post("/login") {
    val loginInfo = KutilKtor.tryReceive<LoginInfo>(call) ?: return@post

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


}
