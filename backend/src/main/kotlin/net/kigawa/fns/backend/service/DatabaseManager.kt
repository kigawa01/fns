package net.kigawa.fns.backend.service

import io.ktor.server.config.*
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.annotation.Kunit
import net.kigawa.kutil.unitapi.component.container.UnitContainer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

@Kunit
class DatabaseManager(
  @ArgName("db.host") private val host: ApplicationConfigValue,
  @ArgName("db.port") private val port: ApplicationConfigValue,
  @ArgName("db.database") private val databaseName: ApplicationConfigValue,
  @ArgName("db.user") private val user: ApplicationConfigValue,
  @ArgName("db.password") private val password: ApplicationConfigValue,
  private val container: UnitContainer,
) {
  private lateinit var database: Database
  fun init() {
    println("connect database: jdbc:mysql://${host.getString()}:${port.getString()}/${databaseName.getString()}")
    database = Database.connect(
      "jdbc:mysql://${host.getString()}:${port.getString()}/${databaseName.getString()}",
      driver = "com.mysql.cj.jdbc.Driver",
      user = user.getString(),
      password = password.getString()
    )

    transaction {
      SchemaUtils.create(*container.getUnitList(Table::class.java).toTypedArray())
    }
  }
}