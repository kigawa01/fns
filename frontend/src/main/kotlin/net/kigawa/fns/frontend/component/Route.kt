package net.kigawa.fns.frontend.component

import net.kigawa.fns.frontend.page.top.Top
import net.kigawa.fns.frontend.page.user.Login
import net.kigawa.fns.frontend.page.user.Register
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.frontend.util.route.KutilRoute
import react.ChildrenBuilder
import react.Props
import react.create
import react.dom.html.ReactHTML
import react.router.dom.RouterProvider


object Route : ComponentBase<Props>() {

  override fun ChildrenBuilder.component(props: Props) {
    ReactHTML.div {
      ThemeProvider.fc {
        ReactHTML.div {

          RouterProvider {
            router = KutilRoute.createBrowserRouter {
              createRoute {
                path = "/"
                element = Header.fc.create()
                createRoute {
                  path = ""
                  element = Top.fc.create()
                }
                createRoute {
                  path = "login"
                  element = Login.fc.create()
                }
                createRoute {
                  path = "register"
                  element = Register.fc.create()
                }
              }
            }
//            router = Root.router
          }

        }
      }
    }
  }

}