package net.kigawa.fns.frontend.component.post

import net.kigawa.fns.frontend.service.ApiClient
import net.kigawa.fns.frontend.util.HttpMethod
import net.kigawa.fns.share.json.post.GetPostRes
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.fns.share.json.post.PostPostRes

object PostManager {

  suspend fun postPost(postPostBody: PostPostBody): Result<PostPostRes> = ApiClient.authedFetchJson(
    "/api/post",
    method = HttpMethod.POST,
    body = postPostBody
  )

  suspend fun getPosts(page: Long, size: Int): Result<List<GetPostRes>> = ApiClient.fetchJson(
    "/api/post",
    method = HttpMethod.GET,
    searchParamsMap = mapOf(
      "page" to page.toString(),
      "size" to size.toString()
    )
  )
}