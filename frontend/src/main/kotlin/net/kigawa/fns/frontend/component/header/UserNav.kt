package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.component.user.UserManager
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Fragment
import react.PropsWithClassName
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.router.dom.Link
import web.cssom.px
import web.cssom.rem

object UserNav : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    val currentUser = UserManager.useUser()


    div {
      css {
        paddingBottom = 5.px
      }

      div {
        css {
          fontSize = 1.4.rem
          ReactHTML.a {
            marginLeft = 20.px
          }
        }
        currentUser.userInfo?.let {
          +it.username
        } ?: Fragment {
          Link {
            to = "/login"
            +"Login"
          }
          Link {
            to = "register"
            +"Register"
          }
        }
      }

    }

  }
}
