package net.kigawa.fns.frontend.component.top

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.dom.html.ReactHTML
import web.cssom.Color
import web.cssom.pct
import web.cssom.px

object Top : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {

    Fragment {
      ReactHTML.div {
        css {
          width = 100.pct
          height = 300.px
          backgroundColor = Color("#ddd")
        }
        ReactHTML.img {
          src = ""
          alt = "top image"
        }
      }

      ReactHTML.section {
        css {
          marginTop = 50.px
        }
        ReactHTML.h2 {
          +"フォロワーの新着作品"
        }
        ReactHTML.div {

        }
      }

      ReactHTML.section {
        ReactHTML.h2 {
          +"新着の作品"
        }
      }

      ReactHTML.section {
        ReactHTML.h2 {
          +"新着の企画"
        }
      }

      ReactHTML.section {
        ReactHTML.h2 {
          +"更新されたコミュニティ"
        }
      }

      ReactHTML.div {

        ReactHTML.section {
          ReactHTML.h2 {
            +"質問・投票"
          }
        }

        ReactHTML.section {
          ReactHTML.h2 {
            +"運営からのお知らせ"
          }
        }


      }
    }
  }
}