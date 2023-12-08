package net.kigawa.fns.frontend.component.header

import emotion.react.css
import net.kigawa.fns.frontend.component.Icon
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.share.Config
import react.ChildrenBuilder
import react.PropsWithClassName
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.h1
import react.router.dom.Link
import web.cssom.Display
import web.cssom.px

object Nav : ComponentBase<PropsWithClassName>() {
  override fun ChildrenBuilder.component(props: PropsWithClassName) {
    ReactHTML.nav {
      ReactHTML.ul {
        css {
          display = Display.flex
        }

        ReactHTML.li {
          Link {
            to = "/"
            css {
              display = Display.flex
            }

            Icon.fc {
              height = 35.px
            }
            h1 {
              +Config.PROJECT_NAME
            }
          }
        }
        ReactHTML.li {
          Link {
            to = "/post"
            +"投稿"
          }
        }

      }
    }
  }

}
