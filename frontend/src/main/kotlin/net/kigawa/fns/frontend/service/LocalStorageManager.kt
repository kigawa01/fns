package net.kigawa.fns.frontend.service

import kotlinx.browser.window
import org.w3c.dom.set

object LocalStorageManager {
  private val storage = window.localStorage
  private const val REFRESH_TOKEN_KEY = "refresh_token"
  fun get(key: String) = storage.getItem(key)
  fun getRefreshToken() = get(REFRESH_TOKEN_KEY)
  fun set(key: String, value: String) = storage.set(key, value)
  fun setRefreshToken(value: String) = set(REFRESH_TOKEN_KEY, value)
}