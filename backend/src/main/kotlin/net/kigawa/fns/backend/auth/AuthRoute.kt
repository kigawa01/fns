package net.kigawa.fns.backend.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.auth.entity.TokenType
import net.kigawa.fns.backend.table.User
import net.kigawa.fns.backend.util.ErrorIDException
import net.kigawa.fns.backend.util.principalTokenResult
import net.kigawa.fns.backend.util.receiveOrThrow
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
      authenticate {
        postRefresh()
      }
    }
  }

  private fun Route.postRefresh() = post("/refresh") {
    val token = call.principalTokenResult().getRefresh()

    call.respond(
      Tokens(
        tokenManager.createToken(token.userid, TokenType.ACCESS),
        tokenManager.createToken(token.userid, TokenType.REFRESH),
      )
    )
  }

  private fun Route.postRegister() = post("/register") {
    val userInfo = call.receiveOrThrow<UserInfo>()

    if (userInfo.password == "") throw ErrorIDException(ErrID.PasswordIsEmpty)

    if (transaction { User.select { User.name eq userInfo.username }.count() } != 0L)
      throw ErrorIDException(ErrID.UserAlreadyExists)

    val id = transaction {
      User.insertAndGetId {
        it[name] = userInfo.username
        it[password] = BCrypt.hashpw(userInfo.password, BCrypt.gensalt())
      }
    }.value

    call.respond(
      Tokens(
        tokenManager.createToken(id, TokenType.ACCESS),
        tokenManager.createToken(id, TokenType.REFRESH),
      )
    )
  }

  private fun Route.postLogin() = post("/login") {
    val loginInfo = call.receiveOrThrow<LoginInfo>()

    val result = transaction {
      User
        .select { User.name eq loginInfo.username }
        .singleOrNull()
    } ?: throw ErrorIDException(ErrID.UserNotExits)

    if (!BCrypt.checkpw(loginInfo.password, result[User.password]))
      throw ErrorIDException(ErrID.InvalidPassword)


    val id = result[User.id].value
    call.respond(
      Tokens(
        tokenManager.createToken(id, TokenType.ACCESS),
        tokenManager.createToken(id, TokenType.REFRESH),
      )
    )
  }


}
