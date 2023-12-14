package net.kigawa.fns.backend.post

import net.kigawa.fns.backend.table.FileTable
import net.kigawa.fns.backend.table.PostTable
import net.kigawa.fns.share.ErrID
import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.json.FileJson
import net.kigawa.fns.share.json.post.GetPostsRes
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.fns.share.json.post.PostPostRes
import net.kigawa.kutil.unitapi.annotation.Kunit
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
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

  fun getPosts(page: Long, size: Int): List<GetPostsRes> = transaction {
    PostTable.selectAll()
      .orderBy(PostTable.createAt)
      .limit(size, page * size)
      .toList()
  }.map {
    createPostRes(it)
  }


  fun getPost(id: Int): GetPostsRes = transaction {
    PostTable.select {
      PostTable.id eq id
    }.firstOrNull()
  }?.let {
    createPostRes(it)
  } ?: throw ErrIDException(ErrID.PostNotFound)


  private fun createPostRes(result: ResultRow) = transaction {
    GetPostsRes(
      result[PostTable.id].value,
      result[PostTable.title],
      FileTable
        .select { FileTable.id eq result[PostTable.file] }
        .first()
        .let { FileJson(it[FileTable.name], it[FileTable.type], it[FileTable.data]) },
      result[PostTable.thumbnail]
        ?.let { FileTable.select { FileTable.id eq it } }
        ?.firstOrNull()
        ?.let { FileJson(it[FileTable.name], it[FileTable.type], it[FileTable.data]) }
    )
  }
}