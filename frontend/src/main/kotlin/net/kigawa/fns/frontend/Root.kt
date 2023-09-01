package net.kigawa.fns.frontend

import net.kigawa.fns.frontend.component.header.Header
import net.kigawa.fns.frontend.hook.theme.ThemeProvidor
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML.div
import react.router.dom.BrowserRouter

object Root : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    div {
      BrowserRouter {
        ThemeProvidor.fc {
          Header {}
        }
      }
    }
  }

}