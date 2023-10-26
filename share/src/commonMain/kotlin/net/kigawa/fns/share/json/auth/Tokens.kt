package net.kigawa.fns.share.json.auth

import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
  val access: String,
  val refresh: String,
)