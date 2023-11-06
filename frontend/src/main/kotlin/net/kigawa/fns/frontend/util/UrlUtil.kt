package net.kigawa.fns.frontend.util

import kotlinx.browser.window
import web.history.history
import web.url.URL

object UrlUtil {

  fun createURL(url: String = "") = URL(url, window.location.href)

  private fun redirect(url: URL) = history.pushState(null, "", url)
  fun redirect(url: String) = redirect(createURL(url))

}