package net.kigawa.fns.backend.auth

import io.ktor.server.auth.*


data class Token(
  val userid: Int,
  val type: String,
) : Principal {
  companion object {
    const val ID_NAME = "userid"
    const val TYPE_NAME = "type"
  }
}




