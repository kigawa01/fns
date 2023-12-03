package net.kigawa.fns.frontend.page.user

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kigawa.fns.frontend.service.ApiClient
import net.kigawa.fns.frontend.service.TokenManager
import net.kigawa.fns.frontend.util.hook.GlobalState
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.auth.Tokens
import net.kigawa.fns.share.json.user.UserInfo

object UserManager {
  private val userState = GlobalState<UserInfo?>(null)

  init {
    CoroutineScope(Dispatchers.Default).launch {
      try {
        refresh().getOrThrow()
      } catch (e: ErrorIDException) {
        if (e.errID == ErrID.NoLogin) return@launch
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
    result.getOrNull()?.let { userState.set(it) }
    return result
  }

  fun useUser(): UserInfo? {
    return userState.use()
  }
}