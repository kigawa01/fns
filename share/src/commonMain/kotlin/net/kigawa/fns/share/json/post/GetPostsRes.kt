package net.kigawa.fns.share.json.post

import kotlinx.serialization.Serializable
import net.kigawa.fns.share.json.FileJson

@Serializable
data class GetPostsRes(
  val id: Int,
  var title: String,
  var file: FileJson,
  var thumbnail: FileJson?,
)