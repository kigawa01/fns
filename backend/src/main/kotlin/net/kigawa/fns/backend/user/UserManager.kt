package net.kigawa.fns.backend.user

import net.kigawa.fns.backend.table.UserTable
import net.kigawa.fns.backend.user.entity.UserAuth
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrorIDException
import net.kigawa.fns.share.json.user.UserInfo
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

@Kunit
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

    if (transaction { UserTable.select { UserTable.name eq userInfo.username }.count() } != 0L) throw ErrorIDException(
      ErrID.UserNameDuplicate
    )
    if (transaction { UserTable.select { UserTable.email eq userInfo.email }.count() } != 0L) throw ErrorIDException(
      ErrID.UserEmailDuplicate
    )

    return transaction {
      UserTable.insertAndGetId {
        it[name] = userInfo.username
        it[email] = userInfo.email
        it[password] = BCrypt.hashpw(userInfo.password, BCrypt.gensalt())
      }
    }.value
  }
}