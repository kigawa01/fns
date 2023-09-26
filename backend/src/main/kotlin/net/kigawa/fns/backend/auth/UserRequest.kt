package net.kigawa.fns.backend.auth

data class UserRequest(
  val email: String,
  val pass: String
)
