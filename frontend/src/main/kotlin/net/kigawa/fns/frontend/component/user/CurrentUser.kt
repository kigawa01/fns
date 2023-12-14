package net.kigawa.fns.frontend.component.user

import net.kigawa.fns.share.json.user.UserInfo

data class CurrentUser(
  val isReady: Boolean,
  val userInfo: UserInfo?,
)