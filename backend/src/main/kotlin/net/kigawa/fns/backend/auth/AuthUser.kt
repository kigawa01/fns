package net.kigawa.fns.backend.auth

import java.security.Principal


data class AuthUser(val id: Int, private val name: String) : Principal {
  override fun getName(): String {
    return name
  }

}
