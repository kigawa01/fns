package net.kigawa.fns.frontend.page.post

import net.kigawa.fns.frontend.component.PageBase
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props

object Post : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    PageBase.fc {
    }
  }
}