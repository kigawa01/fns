package net.kigawa.fns.share

import io.ktor.http.*

enum class ErrID(val statusCode: HttpStatusCode) {
  InvalidPassword(HttpStatusCode.Unauthorized),
  UserNotExits(HttpStatusCode.Unauthorized),
  BadRequest(HttpStatusCode.BadRequest),
  UsernameIsEmpty(HttpStatusCode.BadRequest),
  PasswordIsEmpty(HttpStatusCode.BadRequest),
}
