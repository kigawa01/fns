package net.kigawa.fns.backend.util

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.json.ErrResponse

object KutilKtor {
  suspend inline fun <reified T : Any> tryReceive(call: ApplicationCall): T? {
    return try {
      call.receive<T>()
    } catch (e: BadRequestException) {
      respondErr(call, ErrID.BadRequest)
      null
    }
  }

  fun validationErrIdResult(errID: ErrID) = ValidationResult.Invalid(errID.name)
  suspend fun respondErr(call: ApplicationCall, errID: ErrID, message: String? = null) =
    call.respond(errID.statusCode, createResponse(errID, message))

  private fun createResponse(errID: ErrID, message: String? = null) = ErrResponse(errID, message ?: errID.name)
}