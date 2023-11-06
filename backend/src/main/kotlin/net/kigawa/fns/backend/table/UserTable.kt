package net.kigawa.fns.backend.table

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.dao.id.IntIdTable

@Kunit
object UserTable : IntIdTable() {
  val name = varchar("name", 32).uniqueIndex()
  val password = varchar("password", length = 255)
}