package net.kigawa.fns.frontend.component.post

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ApiHook
import net.kigawa.fns.frontend.util.hook.StyleProvider
import net.kigawa.fns.frontend.util.js.FontSize
import net.kigawa.fns.frontend.util.js.ObjectPosition
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML
import react.router.Navigate
import react.router.useParams
import web.cssom.*

object ShowPost : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val postId = useParams()["postId"]?.toIntOrNull() ?: return Navigate { to = "/notfound" }
    val style = StyleProvider.use()

    val result = ApiHook.useApi {
      PostManager.getPost(postId)
    }
    val res = result.value

    ReactHTML.h2 {
      css {
        fontSize = FontSize(style.hFontSize4)
        margin = Margin(40.px, 40.px)
        borderBottom = Border(2.px, LineStyle.solid, Color(style.line))
        padding = Padding(0.px, 20.px)
      }
      +res?.title
    }
    ReactHTML.div {
      css {
        padding = Padding(40.px, 30.px)
      }
      ReactHTML.img {
        src = res?.file?.fileData
        css {
          maxWidth = 1000.px
          width = 100.pct
          height = 500.px
          objectFit = ObjectFit.contain
          margin = Margin(0.px, Auto.auto)
          objectPosition = ObjectPosition(50.pct, 50.pct)
        }
      }
    }
  }
}