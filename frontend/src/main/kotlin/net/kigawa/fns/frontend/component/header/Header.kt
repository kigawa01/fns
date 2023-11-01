package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.component.Icon
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.share.Config
import react.ChildrenBuilder
import react.PropsWithClassName
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.header
import react.router.dom.Link
import web.cssom.*

object Header : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    val theme = ThemeProvider.use()

    header {
      css(props.className) {
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
      }

      Link {
        to = RouteList.TOP.strPath
        Icon.fc {
          height = 35.px
        }
        h1 {
          css {
            marginLeft = 30.px
            fontSize = 1.6.rem
          }

          +Config.PROJECT_NAME
        }
      }

      div {
        css {
          paddingBottom = 5.px
        }

        if (true) {
          Link {
            css {
              fontSize = 1.4.rem
            }

            to = RouteList.LOGIN.strPath
            +"Login"
          }
        }
      }

    }
  }

}
