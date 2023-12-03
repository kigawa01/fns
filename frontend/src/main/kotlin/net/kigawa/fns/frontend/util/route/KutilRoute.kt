package net.kigawa.fns.frontend.util.route

import remix.run.router.Router

object KutilRoute {
  fun createBrowserRouter(block: BrowserRouterBuilder.() -> Unit): Router {
    val builder = BrowserRouterBuilder()
    block(builder)
    println(builder.toRouter())
    return builder.toRouter()
  }

}