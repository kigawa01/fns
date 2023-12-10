package net.kigawa.fns.backend.table

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.dao.id.IntIdTable

@Kunit
object FileTable : IntIdTable() {
  val name = varchar("name", 32)
  val type = varchar("type", 32)
  val data = mediumText("data")
}