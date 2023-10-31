package net.kigawa.fns.share.json.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
  val username: String = "",
  val password: String = "",
)
