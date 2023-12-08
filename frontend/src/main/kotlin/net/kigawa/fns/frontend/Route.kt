package net.kigawa.fns.frontend

import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.page.post.Post
import net.kigawa.fns.frontend.page.top.Top
import net.kigawa.fns.frontend.page.user.Login
import net.kigawa.fns.frontend.page.user.Register
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.frontend.util.route.KutilRoute
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.create
import react.dom.html.ReactHTML
import react.router.Outlet
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
                element = Fragment.create() {
                  Header.fc {}
                  Outlet {}
                }
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
                createRoute {
                  path = "post"
                  element = Post.fc.create()
                }
              }

            }
          }

        }
      }
    }
  }

}