package net.kigawa.fns.frontend.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kigawa.fns.frontend.util.hook.GlobalState
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
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

  suspend fun refresh(): Result<UserInfo> {
    val result = ApiClient.userInfo()
    result.getOrNull()?.let { userState.set(it) }
    return result
  }

  fun useUser(): UserInfo? {
    return userState.use()
  }
}