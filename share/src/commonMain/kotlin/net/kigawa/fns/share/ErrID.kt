package net.kigawa.fns.share

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
enum class ErrID(val statusCode: HttpStatusCode? = null) {
  InvalidPassword(statusCode = HttpStatusCode.Unauthorized),
  InvalidToken(statusCode = HttpStatusCode.Unauthorized),
  NullTokenID(statusCode = HttpStatusCode.Unauthorized),
  NullTokenType(statusCode = HttpStatusCode.Unauthorized),
  ExpiresToken(statusCode = HttpStatusCode.Unauthorized),
  UnknownTokenType(statusCode = HttpStatusCode.Unauthorized),
  InvalidTokenType(statusCode = HttpStatusCode.Unauthorized),
  UserNotExits(statusCode = HttpStatusCode.Unauthorized),

  BadRequest(statusCode = HttpStatusCode.BadRequest),
  UsernameIsEmpty(statusCode = HttpStatusCode.BadRequest),
  UserEmailIsEmpty(statusCode = HttpStatusCode.BadRequest),
  PostTitleIsEmpty(statusCode = HttpStatusCode.BadRequest),
  PasswordIsEmpty(statusCode = HttpStatusCode.BadRequest),
  InvalidBody(statusCode = HttpStatusCode.BadRequest),

  UserNameDuplicate(statusCode = HttpStatusCode.Conflict),
  UserEmailDuplicate(statusCode = HttpStatusCode.Conflict),
  PostNotFound(statusCode = HttpStatusCode.NotFound),

  NoLogin,
  DeserializeFailed,
}
