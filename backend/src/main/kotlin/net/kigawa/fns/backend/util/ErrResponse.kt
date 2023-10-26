package net.kigawa.fns.backend.util

import kotlinx.serialization.Serializable
import net.kigawa.fns.share.ErrID

@Serializable
data class ErrResponse(
  val errID: String,
  val message: String
) {
  constructor(errID: ErrID, message: String) : this(errID.name, message)
}