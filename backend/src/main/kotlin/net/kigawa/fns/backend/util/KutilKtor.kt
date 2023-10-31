package net.kigawa.fns.backend.util

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import net.kigawa.fns.share.ErrID

object KutilKtor {
  suspend inline fun <reified T : Any> tryReceive(call: ApplicationCall): T? {
    return try {
      call.receive<T>()
    } catch (e: BadRequestException) {
      respondErr(call, ErrID.BadRequest)
      null
    }
  }

  suspend fun respondErr(call: ApplicationCall, errID: ErrID, message: String? = null) {
    call.respond(errID.statusCode, ErrResponse(errID, message ?: errID.name))
  }
}