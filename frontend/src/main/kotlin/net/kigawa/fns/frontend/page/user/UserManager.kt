package net.kigawa.fns.frontend.page.user

import kotlinx.coroutines.flow.MutableStateFlow
import net.kigawa.fns.frontend.service.ApiClient
import net.kigawa.fns.frontend.service.TokenManager
import net.kigawa.fns.frontend.util.concurrent.Coroutines
import net.kigawa.fns.frontend.util.hook.GlobalState
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo

object UserManager {
  private val userState = GlobalState<UserInfo?>(null)
  var isReady = MutableStateFlow(false)
    private set

  init {
    Coroutines.launchIo {
      try {
        refresh().getOrThrow()
      } catch (e: ErrorIDException) {
        if (e.errID == ErrID.NoLogin) return@launchIo
        e.printStackTrace()
      } catch (e: Exception) {
        e.printStackTrace()
      }
      isReady.value = true
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
    result.getOrNull()?.let { userState.set(it) }
    return result
  }

  fun useUser(): UserInfo? {
    return userState.use()
  }
}