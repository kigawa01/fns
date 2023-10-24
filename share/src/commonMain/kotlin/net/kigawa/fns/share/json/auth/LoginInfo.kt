package net.kigawa.fns.share.json.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
  val username: String = "",
  val password: String = "",
)
