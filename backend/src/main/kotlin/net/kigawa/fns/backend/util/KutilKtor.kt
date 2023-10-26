package net.kigawa.fns.backend.util

import io.ktor.server.application.*
import io.ktor.server.response.*
import net.kigawa.fns.share.ErrID

object KutilKtor {
  suspend fun respondErr(call: ApplicationCall, errID: ErrID, message: String? = null) {
    call.respond(errID.statusCode, ErrResponse(errID, message ?: errID.name))
  }
}