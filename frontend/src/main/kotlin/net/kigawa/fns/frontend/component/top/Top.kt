package net.kigawa.fns.frontend.component.top

import emotion.react.css
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.StyleProvider
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.PropsWithClassName
import react.dom.html.ReactHTML
import web.cssom.*

object Top : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val style = StyleProvider.use()
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
//
//      ReactHTML.section {
//        css {
//          marginTop = 50.px
//        }
//        ReactHTML.h2 {
//          +"フォロワーの新着作品"
//        }
//        ReactHTML.div {
//
//        }
//      }

      NewPost.fc {}

//      ReactHTML.section {
//        ReactHTML.h2 {
//          +"新着の企画"
//        }
//      }
//
//      ReactHTML.section {
//        ReactHTML.h2 {
//          +"更新されたコミュニティ"
//        }
//      }

      ReactHTML.div {
        css {
          display = Display.flex
          justifyContent = JustifyContent.spaceBetween
          padding = Padding(20.px, 30.px)
          borderTop = Border(2.px, LineStyle.dotted, Color(style.line))
          margin = Margin(0.px, 20.px)
        }
        val boxStyle: PropsWithClassName.() -> Unit = {
          css {
            flex = Auto.auto
            padding = Padding(10.px, 15.px)

            backgroundColor = Color(style.accent)
            ReactHTML.section {
            }
          }
        }
        ReactHTML.div {
          boxStyle()

          ReactHTML.section {
            ReactHTML.h2 {
              +"質問・投票"
            }
          }
        }
        ReactHTML.div {
          css {
            width = 0.px
            borderLeft = Border(2.px, LineStyle.dotted, Color(style.line))
            margin = Margin(0.px, 20.px)
          }
        }
        ReactHTML.div {
          boxStyle()

          ReactHTML.section {
            ReactHTML.h2 {
              +"運営からのお知らせ"
            }
          }
        }


      }
    }
  }
}