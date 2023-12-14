package net.kigawa.fns.frontend.component.user

import net.kigawa.fns.frontend.service.ApiClient
import net.kigawa.fns.frontend.service.TokenManager
import net.kigawa.fns.frontend.util.hook.GlobalState
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.fns.share.util.concurrent.Coroutines
import net.kigawa.fns.share.util.io.DefaultIo

object UserManager {
  private val userState = GlobalState<CurrentUser>(CurrentUser(false, null))

  init {
    Coroutines.launchIo {
      DefaultIo.info.writeLine("init user manager")
      try {
        refresh().getOrThrow()
      } catch (e: ErrIDException) {
        if (e.errID == ErrID.NoLogin) {
          userState.set(CurrentUser(true, null))
          return@launchIo
        }
        e.printStackTrace()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  suspend fun register(username: String, password: String, email: String): Result<Tokens> {
    val result = ApiClient.register(UserInfo(username, password, email))

    val tokens = result.getOrNull() ?: return result

    TokenManager.setToken(tokens)

    return result
  }

  suspend fun refresh(): Result<UserInfo> {
    val result = ApiClient.userInfo()
    result.getOrNull()?.let { userState.set(CurrentUser(true, it)) }
    return result
  }

  fun useUser(): CurrentUser {
    return userState.use()
  }
}