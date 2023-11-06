package net.kigawa.fns.frontend.service

import net.kigawa.fns.frontend.user.UserManager
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens

object TokenManager {
  private var refreshToken: String? = LocalStorageManager.getRefreshToken()
  var accessToken: String? = null
    private set


  fun isLogin(): Boolean {
    return refreshToken != null
  }

  suspend fun refresh(): Result<Tokens> {
    val result = refreshToken?.let { ApiClient.refresh(it) }
      ?: return Result.failure(ErrorIDException(ErrID.NoLogin))
    val tokens = result.getOrNull() ?: return result

    refreshToken = tokens.refresh
    accessToken = tokens.access

    return result
  }

  suspend fun login(username: String, password: String): Result<Tokens> {
    val result = ApiClient.login(LoginInfo(username, password))

    val tokens = result.getOrNull() ?: return result

    refreshToken = tokens.refresh
    accessToken = tokens.access

    UserManager.refresh()

    return result
  }
}