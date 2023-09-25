package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.component.Icon
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.share.Config
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML
import web.cssom.Color
import web.cssom.Display
import web.cssom.px
import web.cssom.rem

object Header : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val theme = ThemeProvider.use()

    ReactHTML.header {
      css {
        backgroundColor = Color(theme.base)
        paddingLeft = 30.px
        paddingTop = 5.px
        paddingBottom = 5.px
        display = Display.flex
        fontSize = 0.9.rem
        color = Color(theme.textAccent)

        ReactHTML.h1 {
          marginLeft = 50.px
        }
      }
      Icon.fc {
        height = 35.px
      }
      ReactHTML.h1 {
        +Config.PROJECT_NAME
      }
    }
  }

}
