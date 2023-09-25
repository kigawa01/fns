package net.kigawa.fns.frontend

import js.core.jso
import net.kigawa.fns.frontend.page.Login
import net.kigawa.fns.frontend.page.Top
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props
import react.create
import react.router.dom.createBrowserRouter

object Content : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    createBrowserRouter(arrayOf(
      jso {
        path = ""
        element = Top.fc.create()
      },
      jso {
        path = "/login"
        element = Login.fc.create()
      },
    ))
  }

}