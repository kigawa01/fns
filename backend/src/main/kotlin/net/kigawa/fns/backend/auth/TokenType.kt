package net.kigawa.fns.backend.auth

enum class TokenType( val expireHour: Long) {
  ACCESS(1),
  REFRESH(240),
}