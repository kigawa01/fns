package net.kigawa.fns.share.json

import kotlinx.serialization.Serializable
import net.kigawa.fns.share.ErrID

@Serializable
data class ErrResponse(
  val errID: ErrID,
  val message: String
)