package net.kigawa.fns.frontend

import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML

object Root : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    ReactHTML.div {
      ThemeProvider.fc {
        Header.fc {}
        Content.fc {}
      }
    }
  }

}