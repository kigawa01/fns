package net.kigawa.fns.share

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
enum class ErrID(val statusCode: HttpStatusCode) {
  InvalidPassword(HttpStatusCode.Unauthorized),
  InvalidToken(HttpStatusCode.Unauthorized),
  NullTokenID(HttpStatusCode.Unauthorized),
  NullTokenType(HttpStatusCode.Unauthorized),
  ExpiresToken(HttpStatusCode.Unauthorized),
  UnknownTokenType(HttpStatusCode.Unauthorized),
  InvalidTokenType(HttpStatusCode.Unauthorized),
  UserNotExits(HttpStatusCode.Unauthorized),

  BadRequest(HttpStatusCode.BadRequest),
  UsernameIsEmpty(HttpStatusCode.BadRequest),
  PasswordIsEmpty(HttpStatusCode.BadRequest),
  UserAlreadyExists(HttpStatusCode.Conflict),
}
