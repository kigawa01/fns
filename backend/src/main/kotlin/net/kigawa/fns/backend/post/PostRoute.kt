package net.kigawa.fns.backend.post

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.util.receiveOrThrow
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class PostRoute(
  private val postManager: PostManager,
) {
  fun route(route: Route) {
    route.route("/post") {
      getPosts()
      getPost()
      authenticate {
        postPost()
      }
    }
  }

  private fun Route.postPost() = post("") {
    val postPostBody = call.receiveOrThrow<PostPostBody>()

    call.respond(
      postManager.postPost(postPostBody)
    )
  }

  private fun Route.getPosts() = get("") {
    val page = call.parameters["page"]?.toLongOrNull() ?: 0
    val size = call.parameters["size"]?.toIntOrNull() ?: 16
    call.respond(
      postManager.getPosts(page, size)
    )
  }

  private fun Route.getPost() = get("/{id}") {
    val id = call.parameters["id"]!!.toInt()
    call.respond(
      postManager.getPost(id)
    )
  }

}
