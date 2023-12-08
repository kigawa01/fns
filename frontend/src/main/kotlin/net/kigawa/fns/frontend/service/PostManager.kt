package net.kigawa.fns.frontend.service

import net.kigawa.fns.frontend.util.HttpMethod
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.fns.share.json.post.PostPostRes

object PostManager {

  suspend fun postPost(postPostBody: PostPostBody): Result<PostPostRes> = ApiClient.authedFetchJson(
    "/api/post",
    method = HttpMethod.POST,
    body = postPostBody
  )
}