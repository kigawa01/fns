package net.kigawa.fns.frontend.util.route

import js.core.jso
import react.router.RouteObject
import remix.run.router.ActionFunction
import remix.run.router.LazyRouteFunction
import remix.run.router.LoaderFunction
import remix.run.router.ShouldRevalidateFunction

class RouteBuilder : BrowserRouterBuilder() {
  var caseSensitive: Boolean? = null
  var path: String? = null
  var id: String? = null
  var loader: LoaderFunction<Any?>? = null
  var action: ActionFunction<Any?>? = null
  var hasErrorBoundary: Boolean? = null
  var shouldRevalidate: ShouldRevalidateFunction? = null
  var handle: Any? = null
  var index: Boolean? = null
  var element: react.ReactNode? = null
  var errorElement: react.ReactNode? = null
  var Component: react.ComponentType<*>? = null
  var ErrorBoundary: react.ComponentType<*>? = null
  var lazy: LazyRouteFunction<RouteObject>? = null
  fun toRouteObject(): RouteObject {
    return jso {
      caseSensitive = this@RouteBuilder.caseSensitive
      path = this@RouteBuilder.path
      id = this@RouteBuilder.id
      loader = this@RouteBuilder.loader
      action = this@RouteBuilder.action
      hasErrorBoundary = this@RouteBuilder.hasErrorBoundary
      shouldRevalidate = this@RouteBuilder.shouldRevalidate
      handle = this@RouteBuilder.handle
      index = this@RouteBuilder.index
      children = this@RouteBuilder.children.toTypedArray()
      element = this@RouteBuilder.element
      errorElement = this@RouteBuilder.errorElement
      Component = this@RouteBuilder.Component
      ErrorBoundary = this@RouteBuilder.ErrorBoundary
      lazy = this@RouteBuilder.lazy
    }
  }
}