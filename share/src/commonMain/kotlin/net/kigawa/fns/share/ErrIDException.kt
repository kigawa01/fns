package net.kigawa.fns.share

open class ErrIDException(
  val errID: ErrID,
  message: String = errID.name,
  cause: Throwable? = null,
) : RuntimeException(cause) {
  override val message: String = "id: $errID, message: $message"
}