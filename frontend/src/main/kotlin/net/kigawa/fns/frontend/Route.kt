package net.kigawa.fns.frontend

import net.kigawa.fns.frontend.component.NotFound
import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.component.post.PostPost
import net.kigawa.fns.frontend.component.post.ShowPost
import net.kigawa.fns.frontend.component.top.Top
import net.kigawa.fns.frontend.component.user.Login
import net.kigawa.fns.frontend.component.user.Register
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.StyleProvider
import net.kigawa.fns.frontend.util.route.KutilRoute
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.create
import react.dom.html.ReactHTML
import react.router.Navigate
import react.router.Outlet
import react.router.dom.RouterProvider


object Route : ComponentBase<Props>() {

  override fun ChildrenBuilder.component(props: Props) {
    ReactHTML.div {
      StyleProvider.fc {
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
                  path = "post/"
                  element = PostPost.fc.create()
                }
                createRoute {
                  path = "post/:postId"
                  element = ShowPost.fc.create()
                }
                createRoute {
                  path = "notfound"
                  element = NotFound.fc.create()
                }
                createRoute {
                  path = "*"
                  element = Navigate.create { to = "/notfound" }
                }
              }

            }
          }

        }
      }
    }
  }

}