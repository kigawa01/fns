package net.kigawa.fns.frontend.util.route

import react.router.RouteObject
import react.router.dom.createBrowserRouter
import remix.run.router.Router

open class BrowserRouterBuilder {
  var children: MutableList<RouteObject> = mutableListOf()

  fun createRoute(block: RouteBuilder.() -> Unit) {
    val builder = RouteBuilder()
    block(builder)
    children.add(builder.toRouteObject())
  }

  fun toRouter(): Router {
    return createBrowserRouter(children.toTypedArray())
  }
}