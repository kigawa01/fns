package net.kigawa.fns.frontend.service

import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.entity.User
import net.kigawa.fns.frontend.util.hook.GlobalState

object UserManager {
  private val userSate = GlobalState<User?>(null)
  fun use(): User? {
    return userSate.use()
  }

  fun set(user: User?) {
    userSate.set(user)
  }

  fun userOrLogin(): User? {
    val user = use()
    if (user == null) {
      RouteList.LOGIN.access()
    }
    return user
  }
}