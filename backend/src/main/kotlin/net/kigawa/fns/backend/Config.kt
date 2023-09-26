package net.kigawa.fns.backend

import io.ktor.server.application.*
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class Config(private val environment: ApplicationEnvironment) {
  val secret = environment.config.property("jwt.secret").getString()
  val issuer = environment.config.property("jwt.issuer").getString()
  val audience = environment.config.property("jwt.audience").getString()
  val myRealm = environment.config.property("jwt.realm").getString()
}