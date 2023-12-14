package net.kigawa.fns.frontend.util.hook

data class ApiResult<R>(
  val err: String?,
  val value: R,
)