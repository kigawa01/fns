package net.kigawa.fns.backend.user

import net.kigawa.fns.backend.table.UserTable
import net.kigawa.fns.backend.user.entity.UserAuth
import net.kigawa.fns.backend.util.ErrorIDException
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.json.user.UserInfo
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserManager {
  fun userInfo(userId: Int): UserInfo {
    val result = transaction {
      UserTable.select {
        UserTable.id eq userId
      }.firstOrNull()
    } ?: throw ErrorIDException(ErrID.UserNotExits)
    return UserInfo(result[UserTable.name])
  }

  fun userAuth(username: String): UserAuth {
    val result = transaction {
      UserTable.select {
        UserTable.name eq username
      }.firstOrNull()
    } ?: throw ErrorIDException(ErrID.UserNotExits)
    return UserAuth(result[UserTable.id].value, result[UserTable.name], result[UserTable.password])
  }

  fun register(userInfo: UserInfo): Int {
    if (userInfo.password == "") throw ErrorIDException(ErrID.PasswordIsEmpty)

    if (transaction { UserTable.select { UserTable.name eq userInfo.username }.count() } != 0L)
      throw ErrorIDException(ErrID.UserAlreadyExists)

    return transaction {
      UserTable.insertAndGetId {
        it[name] = userInfo.username
        it[password] = BCrypt.hashpw(userInfo.password, BCrypt.gensalt())
      }
    }.value
  }
}