package net.kigawa.fns.frontend.page

import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props
import react.router.Navigate

object Top : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {

    if (true) {
      Navigate {
        replace
        to = RouteList.LOGIN.strPath
      }
      return
    }

    PageBase.fc {
      +"aaa top"
    }
  }
}