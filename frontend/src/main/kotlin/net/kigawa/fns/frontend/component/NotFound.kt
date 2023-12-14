package net.kigawa.fns.frontend.component

import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.dom.html.ReactHTML
import react.router.dom.Link

object NotFound : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    Fragment {
      ReactHTML.h2 {
        +"404 Not Found"
      }
      ReactHTML.ul {
        ReactHTML.li {
          Link {
            to = "/"
            +"Top"
          }
        }
      }
    }
  }
}