package net.kigawa.fns.frontend.page

import emotion.react.css
import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.component.Icon
import net.kigawa.fns.frontend.user.UserManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.share.Config
import react.ChildrenBuilder
import react.Fragment
import react.PropsWithClassName
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.header
import react.router.dom.Link
import web.cssom.*

object Header : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    val userInfo = UserManager.useUser()
    header {
      style(props)

      Link {
        to = RouteList.TOP.strPath
        Icon.fc {
          height = 35.px
        }
        h1 {
          +Config.PROJECT_NAME
        }
      }

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
          userInfo?.let {
            +userInfo.username
          } ?: Fragment {
            Link {
              to = RouteList.LOGIN.strPath
              +"Login"
            }
            Link {
              to = RouteList.REGISTER.strPath
              +"Register"
            }
          }
        }

      }

    }
  }

  private fun PropsWithClassName.style(props: PropsWithClassName) {
    val theme = ThemeProvider.use()
    return css(props.className) {
      backgroundColor = Color(theme.main)
      paddingLeft = 30.px
      paddingRight = 30.px
      paddingTop = 5.px
      paddingBottom = 6.px
      boxSizing = BoxSizing.borderBox
      width = 100.pct
      color = Color(theme.textAccent)
      display = Display.flex
      justifyContent = JustifyContent.spaceBetween

      "> *" {
        marginTop = Auto.auto
        display = Display.flex
      }
      ReactHTML.h1 {
        marginLeft = 30.px
        fontSize = 1.6.rem
      }

    }
  }

}
