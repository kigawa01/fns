package net.kigawa.fns.frontend.page

import js.core.jso
import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.user.Login
import net.kigawa.fns.frontend.user.Register
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
        path = RouteList.REGISTER.strPath
        element = Register.fc.create()
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
        ReactHTML.div {
          RouterProvider {
            router = Root.router
          }
        }
      }
    }
  }

}