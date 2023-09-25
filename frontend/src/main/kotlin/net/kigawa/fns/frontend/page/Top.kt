package net.kigawa.fns.frontend.page

import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props

object Top : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    PageBase.fc {
      + "aaa top"
    }
  }
}