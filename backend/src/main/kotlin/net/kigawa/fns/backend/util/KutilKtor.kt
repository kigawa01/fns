package net.kigawa.fns.backend.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object KutilKtor {
  suspend fun respondErr(call: ApplicationCall, statusCode: HttpStatusCode, response: ErrResponse) {
    call.respond(statusCode, response)
  }

  suspend fun resourceNotFound(call: ApplicationCall, message: String) =
    KutilKtor.resourceNotFound(call, ErrResponse(message))

  suspend fun resourceNotFound(call: ApplicationCall, response: ErrResponse) =
    respondErr(call, HttpStatusCode.NotFound, response)

}