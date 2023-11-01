package net.kigawa.fns.frontend.page

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML
import web.cssom.Auto
import web.cssom.Color
import web.cssom.px

object Login : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val theme = ThemeProvider.use()

    PageBase.fc {
      ReactHTML.div {
        css {
          marginLeft = Auto.auto
          marginRight = Auto.auto
          width = 400.px
          height = 450.px
          backgroundColor = Color(theme.base2)
        }
        ReactHTML.h2 {
          +"log in"
        }
        ReactHTML.input {
        }
        ReactHTML.input {
        }
      }
    }
  }

}