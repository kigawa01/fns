package net.kigawa.fns.frontend.service

import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.auth.Tokens

object TokenManager {
  private var refreshToken: String? = LocalStorageManager.getRefreshToken()
  private var accessToken: String? = null

  suspend fun login(username: String, password: String): Result<Tokens> {
    val result = ApiClient.login(LoginInfo(username, password))

    val tokens = result.getOrNull()
    if (tokens != null) {
      refreshToken = tokens.refresh
      accessToken = tokens.access
    }

    return result
  }
}