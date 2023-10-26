package net.kigawa.fns.backend.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.annotation.Kunit
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Kunit
class TokenManager(
  @ArgName("jwt.secret") private val secret: ApplicationConfigValue,
  @ArgName("jwt.audience") private val audience: ApplicationConfigValue,
  @ArgName("jwt.issuer") private val issuer: ApplicationConfigValue,
) {
  private val algorithm = Algorithm.HMAC256(secret.getString())
  fun loadModule(application: Application) {

    application.authentication {
      jwt {
        realm = javaClass.packageName
        verifier(
          JWT
            .require(algorithm)
            .withAudience()
            .withIssuer(issuer.getString())
            .build()
        )
        validate { credential ->
          val id = credential.payload.getClaim(TokenPrincipal.ID_NAME)
          if (id.isNull) return@validate null
          val type = credential.payload.getClaim(TokenPrincipal.TYPE_NAME)
          if (type.isNull) return@validate null
          return@validate TokenPrincipal(id.asInt(), type.asString())
        }
      }

    }

  }

  fun createToken(id: Int, type: TokenType): String {
    return JWT.create()
      .withAudience(audience.getString())
      .withExpiresAt(Date.from(LocalDateTime.now().plusHours(type.expireHour).toInstant(ZoneOffset.UTC)))
      .withClaim(TokenPrincipal.ID_NAME, id)
      .withClaim(TokenPrincipal.TYPE_NAME, type.name)
      .withIssuer(issuer.getString())
      .sign(algorithm)
  }
}