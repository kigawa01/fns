package net.kigawa.fns.frontend

import net.kigawa.fns.frontend.util.UrlUtil
import web.history.history
import web.url.URL

enum class RouteList(val strPath: String) {
  LOGIN("/login"),
  TOP("/"),
  ;

  fun url(): URL {
    return UrlUtil.createURL(strPath)
  }

  fun access() {
    history.pushState(null, "", url())
  }
}