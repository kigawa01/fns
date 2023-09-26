package net.kigawa.fns.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import net.kigawa.fns.backend.plugins.Routes
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Modules(
  private val routes: Routes
) {
  fun module(application: Application) {
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = "jwt-audience"
    val jwtDomain = "https://jwt-provider-domain/"
    val jwtRealm = "ktor sample app"
    val jwtSecret = "secret"
    application.authentication {
      jwt {
        realm = jwtRealm
        verifier(
          JWT
            .require(Algorithm.HMAC256(jwtSecret))
            .withAudience(jwtAudience)
            .withIssuer(jwtDomain)
            .build()
        )
        validate { credential ->
          if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
        }
      }
    }
    routes.configureRouting(application)
  }
}

fun Application.module() {
  App.app.registerModules(this).module(this)
}