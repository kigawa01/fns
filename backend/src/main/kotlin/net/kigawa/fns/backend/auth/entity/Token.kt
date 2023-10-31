package net.kigawa.fns.backend.auth.entity

import io.ktor.server.auth.*


data class Token(
  val userid: Int,
  val type: TokenType,
) : Principal {
  companion object {
    const val ID_NAME = "userid"
    const val TYPE_NAME = "type"
  }
}




