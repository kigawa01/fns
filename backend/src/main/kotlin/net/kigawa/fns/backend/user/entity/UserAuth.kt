package net.kigawa.fns.backend.user.entity

data class UserAuth(
  val id: Int,
  val name: String,
  val hashedPassword: String,
)