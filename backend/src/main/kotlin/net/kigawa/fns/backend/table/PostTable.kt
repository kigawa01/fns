package net.kigawa.fns.backend.table

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.dao.id.IntIdTable

@Kunit
object PostTable : IntIdTable() {
  val title = varchar("title", 32)
  val file = reference("file", FileTable)
  val thumbnail = reference("thumbnail", FileTable).nullable()
}