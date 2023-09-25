package net.kigawa.fns.frontend.page

import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.PropsWithChildren
import react.dom.html.ReactHTML

object PageBase : ComponentBase<PropsWithChildren>() {
  override fun ChildrenBuilder.component(props: PropsWithChildren) {
    ReactHTML.div {
      Header.fc {}
    }
  }
}