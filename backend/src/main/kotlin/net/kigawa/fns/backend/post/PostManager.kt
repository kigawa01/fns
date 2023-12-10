package net.kigawa.fns.backend.post

import net.kigawa.fns.backend.table.FileTable
import net.kigawa.fns.backend.table.PostTable
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.fns.share.json.post.PostPostRes
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

@Kunit
class PostManager {

  fun postPost(postPostBody: PostPostBody): PostPostRes {
    if (postPostBody.title == "") throw ErrIDException(ErrID.PostTitleIsEmpty)

    val id = transaction {
      val fileId = FileTable.insertAndGetId {
        it[name] = postPostBody.file.filename
        it[type] = postPostBody.file.fileType
        it[data] = postPostBody.file.fileData
      }
      val thumbnailId = postPostBody.thumbnail?.let { fileJson ->
        FileTable.insertAndGetId {
          it[name] = fileJson.filename
          it[type] = fileJson.fileType
          it[data] = fileJson.fileData
        }
      }
      return@transaction PostTable.insertAndGetId {
        it[title] = postPostBody.title
        it[file] = fileId
        it[thumbnail] = thumbnailId
      }
    }
    return PostPostRes(id.value)
  }
}