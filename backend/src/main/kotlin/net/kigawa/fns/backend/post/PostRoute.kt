package net.kigawa.fns.backend.post

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.kigawa.fns.backend.auth.TokenManager
import net.kigawa.fns.backend.user.UserManager
import net.kigawa.fns.backend.util.receiveOrThrow
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.kutil.unitapi.annotation.Kunit

@Kunit
class PostRoute(
  private val tokenManager: TokenManager,
  private val userManager: UserManager,
  private val postManager: PostManager,
) {
  fun route(route: Route) {
    route.route("/post") {
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


}
