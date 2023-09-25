package net.kigawa.fns.frontend.component

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import web.cssom.Auto
import web.cssom.Display
import web.cssom.Length
import web.cssom.px

object Icon : ComponentBase<IconProps>() {
  override fun ChildrenBuilder.component(props: IconProps) {
    div {
      img {
        src = "/image/icon.png"
        css {
          height = props.height ?: 30.px
          width = Auto.auto
          borderRadius = 5.px
          display = Display.block
        }
      }
    }
  }

}

external interface IconProps : Props {
  var height: Length?
}
