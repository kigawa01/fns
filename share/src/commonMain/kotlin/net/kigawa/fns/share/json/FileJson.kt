package net.kigawa.fns.share.json

import kotlinx.serialization.Serializable

@Serializable
data class FileJson(
  var filename: String,
  var fileType: String,
  var fileData: String,
)
