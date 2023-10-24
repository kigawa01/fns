package net.kigawa.fns.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import net.kigawa.fns.backend.auth.Token
import net.kigawa.fns.backend.route.Routing
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Modules(
  private val routes: Routing,
  private val environment: ApplicationEnvironment,
  @ArgName("jwt.secret") private val secret: ApplicationConfigValue,
  @ArgName("jwt.issuer") private val issuer: ApplicationConfigValue,
  @ArgName("jwt.audience") private val audience: ApplicationConfigValue,
) {
  fun module(application: Application) {
    application.install(Resources)
    application.install(ContentNegotiation) {
      json()
    }

    application.authentication {
      jwt {
        realm = javaClass.packageName
        verifier(
          JWT
            .require(Algorithm.HMAC256(secret.getString()))
            .withAudience()
            .withIssuer(issuer.getString())
            .build()
        )
        validate { credential ->
          val id = credential.payload.getClaim(Token.ID_NAME)
          if (id.isNull) return@validate null
          val type = credential.payload.getClaim(Token.TYPE_NAME)
          if (type.isNull)return@validate null
          return@validate Token(id.asInt(),type.asString())
        }
      }
    }

    routes.configureRouting(application)
  }
}

fun Application.module() {
  FnsApplication.app.registerModules(this).module(this)
}