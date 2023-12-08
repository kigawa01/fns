package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.page.user.UserManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import react.ChildrenBuilder
import react.PropsWithClassName
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.header
import web.cssom.*

object Header : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    val userInfo = UserManager.useUser()

    header {
      style(props)
      Nav.fc {}
      UserNav.fc {}
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
