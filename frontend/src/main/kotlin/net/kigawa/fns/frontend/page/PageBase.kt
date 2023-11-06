package net.kigawa.fns.frontend.page

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.PropsWithChildren
import react.PropsWithClassName
import react.dom.html.ReactHTML
import web.cssom.*

external interface PageBaseProps : PropsWithChildren, PropsWithClassName
object PageBase : ComponentBase<PageBaseProps>() {
  override fun ChildrenBuilder.component(props: PageBaseProps) {
    ReactHTML.div {
      css {
        flexDirection = FlexDirection.column
      }
      Header.fc {
        css {
          top = 0.px
          left = 0.px
          position = Position.fixed
        }
      }
      ReactHTML.div {
        css(props.className) {
          boxSizing = BoxSizing.borderBox
          paddingTop = 45.px
          minHeight = 100.vh
        }
        +props.children
      }
    }
  }
}