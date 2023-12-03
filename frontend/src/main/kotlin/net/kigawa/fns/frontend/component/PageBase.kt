package net.kigawa.fns.frontend.component

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.PropsWithChildren
import react.PropsWithClassName
import react.dom.html.ReactHTML
import web.cssom.BoxSizing
import web.cssom.FlexDirection
import web.cssom.px
import web.cssom.vh

external interface PageBaseProps : PropsWithChildren, PropsWithClassName
object PageBase : ComponentBase<PageBaseProps>() {
  override fun ChildrenBuilder.component(props: PageBaseProps) {
    ReactHTML.div {
      css {
        flexDirection = FlexDirection.column
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