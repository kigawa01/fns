package net.kigawa.fns.frontend.util

import kotlinx.browser.window
import web.url.URL

object UrlUtil {
  fun createURL(url: URL): URL {
    return URL(url, window.location.href)
  }

  fun createURL(url: String): URL {
    return URL(url, window.location.href)
  }
}