package net.kigawa.fns.backend.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import net.kigawa.fns.backend.auth.entity.TokenResult
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.ErrResponse

object KutilKtor {

  fun validationErrIdResult(errID: ErrID) = ValidationResult.Invalid(errID.name)

  fun createResponse(errID: ErrID, message: String? = null) = ErrResponse(errID, message ?: errID.name)
}

suspend inline fun <reified T : Any> ApplicationCall.receiveOrThrow(): T {
  return try {
    receive<T>()
  } catch (e: BadRequestException) {
    throw ErrorIDException(ErrID.InvalidBody)
  }
}

suspend fun ApplicationCall.respondErr(errID: ErrID, message: String? = null) =
  respond(errID.statusCode ?: HttpStatusCode.InternalServerError, KutilKtor.createResponse(errID, message))

fun ApplicationCall.principalTokenResult() =
  principal<TokenResult>() ?: throw ErrorIDException(ErrID.InvalidToken)