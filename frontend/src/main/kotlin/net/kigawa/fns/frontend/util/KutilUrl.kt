package net.kigawa.fns.frontend.util

import kotlinx.browser.window
import web.url.URL

object KutilUrl {

  fun createURL(url: String = "") = URL(url, window.location.href)

}