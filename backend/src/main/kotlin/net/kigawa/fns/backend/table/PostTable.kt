package net.kigawa.fns.backend.table

import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

@Kunit
object PostTable : IntIdTable() {
  val title = varchar("title", 32)
  val file = reference("file", FileTable)
  val thumbnail = reference("thumbnail", FileTable).nullable()
  val createAt = datetime("create_at").apply { defaultValueFun = { DateTime.now() } }
}