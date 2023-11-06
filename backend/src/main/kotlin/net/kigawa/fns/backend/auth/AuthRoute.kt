package net.kigawa.fns.backend.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.auth.entity.TokenType
import net.kigawa.fns.backend.user.UserManager
import net.kigawa.fns.backend.util.principalTokenResult
import net.kigawa.fns.backend.util.receiveOrThrow
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.mindrot.jbcrypt.BCrypt

@Kunit
class AuthRoute(
  private val tokenManager: TokenManager,
  private val userManager: UserManager,
) {
  fun route(route: Route) {
    route.route("/auth") {
      postLogin()
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


  private fun Route.postLogin() = post("/login") {
    val loginInfo = call.receiveOrThrow<LoginInfo>()
    val userAuth = userManager.userAuth(loginInfo.username)
    if (!BCrypt.checkpw(loginInfo.password, userAuth.hashedPassword))
      throw ErrorIDException(ErrID.InvalidPassword)


    call.respond(
      Tokens(
        tokenManager.createToken(userAuth.id, TokenType.ACCESS),
        tokenManager.createToken(userAuth.id, TokenType.REFRESH),
      )
    )
  }


}
