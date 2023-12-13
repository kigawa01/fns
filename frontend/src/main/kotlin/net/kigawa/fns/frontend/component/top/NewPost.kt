package net.kigawa.fns.frontend.component.top

import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.dom.html.ReactHTML

object NewPost : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {

    Fragment {
      ReactHTML.section {
        ReactHTML.h2 {
          +"新着の作品"
        }

        ReactHTML.div {

        }
      }
    }
  }
}