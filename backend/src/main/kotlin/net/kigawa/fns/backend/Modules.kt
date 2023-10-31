package net.kigawa.fns.backend

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import net.kigawa.fns.backend.auth.TokenManager
import net.kigawa.fns.backend.service.DatabaseManager
import net.kigawa.fns.backend.service.Routing
import net.kigawa.fns.backend.util.ErrorIDException
import net.kigawa.fns.backend.util.KutilKtor
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.json.auth.LoginInfo
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Modules(
  private val routes: Routing,
  private val databaseManager: DatabaseManager,
  private val tokenManager: TokenManager,
) {
  fun loadModule(application: Application) {
    application.install(Resources)
    application.install(ContentNegotiation) {
      json()
    }
    application.installValidation()
    application.installStatusPages()

    tokenManager.loadModule(application)
    routes.configureRouting(application)
    databaseManager.init()
  }

  private fun Application.installValidation() = install(RequestValidation) {
    validate<LoginInfo> {

      if (it.username == "") return@validate KutilKtor.validationErrIdResult(ErrID.UsernameIsEmpty)
      if (it.password == "") return@validate KutilKtor.validationErrIdResult(ErrID.PasswordIsEmpty)

      return@validate ValidationResult.Valid
    }
    validate<UserInfo> {
      if (it.username == "") return@validate KutilKtor.validationErrIdResult(ErrID.UsernameIsEmpty)

      return@validate ValidationResult.Valid
    }

  }

  private fun Application.installStatusPages() = install(StatusPages) {
    exception<RequestValidationException> { call, cause ->
      try {
        cause.reasons.firstOrNull()
          ?.let { KutilKtor.respondErr(call, ErrID.valueOf(it)) }
          ?: call.respondInternalErr(cause)
      } catch (e: IllegalArgumentException) {
        call.respondInternalErr(cause)
      }
    }
    exception<ErrorIDException> { call, cause ->
      KutilKtor.respondErr(call, cause.errID, cause.message)
    }
    exception<Throwable> { call, cause ->
      call.respondInternalErr(cause)
    }

  }

  private suspend fun ApplicationCall.respondInternalErr(cause: Throwable) =
    respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)

}

fun Application.loadModule() {
  FnsApplication.app.loadModule(this)
}