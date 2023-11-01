package net.kigawa.fns.frontend.service

import net.kigawa.fns.frontend.util.hook.GlobalState

object TokenManager {
  private val refreshTokenSate = GlobalState(LocalStorageManager.getRefreshToken())
  private val accessTokenSate = GlobalState(null)
}