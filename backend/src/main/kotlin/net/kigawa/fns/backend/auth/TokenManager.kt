package net.kigawa.fns.backend.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import net.kigawa.fns.backend.auth.entity.Token
import net.kigawa.fns.backend.auth.entity.TokenResult
import net.kigawa.fns.backend.auth.entity.TokenType
import net.kigawa.fns.backend.util.respondErr
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.util.KutilResult
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
            .withAudience(audience.getString())
            .withIssuer(issuer.getString())
            .build()
        )
        validate { credential ->
          KutilResult.tryResult(ErrorIDException::class) {
            val id = credential.payload.getClaim(Token.ID_NAME)
            if (id.isNull) throw ErrorIDException(ErrID.NullTokenID)
            val type = credential.payload.getClaim(Token.TYPE_NAME)
            if (type.isNull) throw ErrorIDException(ErrID.NullTokenType)
            val expiresDate = credential.expiresAt
            if (expiresDate != null && LocalDateTime.now().toInstant(ZoneOffset.UTC).isAfter(expiresDate.toInstant()))
              throw ErrorIDException(ErrID.ExpiresToken)
            try {
              return@tryResult Token(id.asInt(), TokenType.valueOf(type.asString()))
            } catch (_: IllegalArgumentException) {
              throw ErrorIDException(ErrID.UnknownTokenType)
            }
          }.let { TokenResult(it) }
        }
        challenge { _, _ ->
          call.respondErr(ErrID.InvalidToken)
        }

      }

    }

  }

  fun createToken(id: Int, type: TokenType): String {
    return JWT.create()
      .withAudience(audience.getString())
      .withExpiresAt(Date.from(LocalDateTime.now().plusHours(type.expireHour).toInstant(ZoneOffset.UTC)))
      .withClaim(Token.ID_NAME, id)
      .withClaim(Token.TYPE_NAME, type.name)
      .withIssuer(issuer.getString())
      .sign(algorithm)
  }
}