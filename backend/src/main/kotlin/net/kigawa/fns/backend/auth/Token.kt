package net.kigawa.fns.backend.auth

import io.ktor.server.auth.*


data class Token(
  val id: Int,
  val type: String,
) : Principal {
  companion object {
    const val ID_NAME = "id"
    const val TYPE_NAME = "type"
  }
}




