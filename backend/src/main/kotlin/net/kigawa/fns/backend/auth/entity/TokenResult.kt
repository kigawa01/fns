package net.kigawa.fns.backend.auth.entity

import io.ktor.server.auth.*
import net.kigawa.fns.backend.util.ErrorIDException
import net.kigawa.fns.share.ErrID

class TokenResult(
  private val result: Result<Token>
) : Principal {
  fun getOrThrow() = result.getOrThrow()
  fun getTypeCheck(type: TokenType): Token {
    val token = getOrThrow()
    if (token.type == type) return token
    throw ErrorIDException(ErrID.InvalidTokenType)
  }

  fun getRefresh() = getTypeCheck(TokenType.REFRESH)
  fun getAccess() = getTypeCheck(TokenType.ACCESS)
}