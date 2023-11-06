package net.kigawa.fns.backend.user

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.auth.TokenManager
import net.kigawa.fns.backend.auth.entity.TokenType
import net.kigawa.fns.backend.util.principalTokenResult
import net.kigawa.fns.backend.util.receiveOrThrow
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class UserRoute(
  private val tokenManager: TokenManager,
  private val userManager: UserManager,
) {
  fun route(route: Route) {
    route.route("/user") {
      postRegister()
      authenticate {
        getUser()
      }
    }
  }

  private fun Route.getUser() = get {
    val token = call.principalTokenResult().getAccess()
    val userInfo = userManager.userInfo(token.userid)
    call.respond(userInfo)
  }

  private fun Route.postRegister() = post("/register") {
    val userInfo = call.receiveOrThrow<UserInfo>()
    val id = userManager.register(userInfo)

    call.respond(
      Tokens(
        tokenManager.createToken(id, TokenType.ACCESS),
        tokenManager.createToken(id, TokenType.REFRESH),
      )
    )
  }


}
