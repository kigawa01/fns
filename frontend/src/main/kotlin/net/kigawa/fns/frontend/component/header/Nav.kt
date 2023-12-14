package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.component.Icon
import net.kigawa.fns.frontend.component.user.UserManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.StyleProvider
import net.kigawa.fns.frontend.util.js.FontSize
import net.kigawa.fns.share.Config
import react.ChildrenBuilder
import react.PropsWithClassName
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.h1
import react.router.dom.Link
import web.cssom.*

object Nav : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    val style = StyleProvider.use()
    val user = UserManager.useUser()

    ReactHTML.nav {
      ReactHTML.ul {
        css {
          display = Display.flex
          alignItems = AlignItems.center
        }

        ReactHTML.li {
          Link {
            to = "/"
            css {
              display = Display.flex
              alignItems = AlignItems.center
            }

            Icon.fc {
              height = 35.px
            }
            h1 {
              css {
                margin = Margin(0.px, 30.px)
                fontSize = FontSize(style.hFontSize2)
              }
              +Config.PROJECT_NAME
            }
          }
        }

        if (user.userInfo != null) ReactHTML.li {
          Link {
            css {
              margin = Margin(0.px, 10.px)
              padding = Padding(0.px, 15.px)
              fontSize = FontSize(style.hFontSize1)
              borderBottom = Border(2.px, LineStyle.solid, Color(style.textLight))
            }
            to = "/post"
            +"投稿"
          }
        }

      }
    }
  }

}
