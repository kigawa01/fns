package net.kigawa.fns.frontend.page

import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.PropsWithChildren

object PageBase : ComponentBase<PropsWithChildren>() {
  override fun ChildrenBuilder.component(props: PropsWithChildren) {
    Header.fc {}
    +props.children
  }
}