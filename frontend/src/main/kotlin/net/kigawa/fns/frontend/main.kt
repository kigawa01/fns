package net.kigawa.fns.frontend

import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
  document
    .getElementById("react")
    ?.let { createRoot(it) }
    ?.render(Route.fc.create())
    ?: document.write("ページのロードに失敗しました")

}
