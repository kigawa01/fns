package net.kigawa.fns.frontend

import js.core.jso
import net.kigawa.fns.frontend.page.Login
import net.kigawa.fns.frontend.page.Top
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import react.ChildrenBuilder
import react.Props
import react.create
import react.dom.html.ReactHTML
import react.router.RouterProvider
import react.router.dom.createBrowserRouter

object Root : ComponentBase<Props>() {
  private val router = createBrowserRouter(
    arrayOf(
      jso {
        path = RouteList.LOGIN.strPath
        element = Login.fc.create()
      },
      jso {
        path = RouteList.TOP.strPath
        element = Top.fc.create()
      },
    )
  )

  override fun ChildrenBuilder.component(props: Props) {


    ReactHTML.div {
      ThemeProvider.fc {
        RouterProvider {
          router = this@Root.router
        }
      }
    }
  }

}