package net.kigawa.fns.frontend.component.top

import emotion.react.css
import net.kigawa.fns.frontend.component.post.PostManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.hook.ApiHook
import net.kigawa.fns.frontend.util.hook.StyleProvider
import net.kigawa.fns.frontend.util.js.FontSize
import net.kigawa.fns.frontend.util.js.ObjectPosition
import net.kigawa.fns.share.json.post.GetPostsRes
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.dom.html.ReactHTML
import react.router.dom.Link
import web.cssom.*

object NewPost : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val style = StyleProvider.use()

    val result = ApiHook.useApi(listOf()) {
      getPosts()
    }


    Fragment {
      ReactHTML.section {
        css {
          margin = Margin(30.px, 30.px)
        }
        ReactHTML.div {
          css {
            display = Display.flex
            margin = Margin(20.px, 10.px)
          }
          ReactHTML.h2 {
            +"新着の作品"
          }
          Link {
            css {
              border = Border(2.px, LineStyle.solid, Color(style.accent2))
              display = Display.block
              height = Length.fitContent
              fontSize = FontSize(style.hFontSize1)
              margin = Margin(0.px, 0.px, 0.px, 40.px)
            }
            +"もっと見る"
          }
        }
        result.err?.let {
          ReactHTML.p {
            ReactHTML.strong {
              +it
            }
          }
        }
        ReactHTML.div {
          css {
            display = Display.flex
            overflowX = Overflow.scroll
            padding = Padding(0.px, 0.px, 10.px, 0.px)
          }
          result.value.map {
            val thumbnail = it.thumbnail ?: it.file

            Link {
              to = "/post/${it.id}"
              key = it.title
              css {
                backgroundColor = Color(style.base2)
                border = Border(1.px, LineStyle.solid, Color(style.line))
                width = 300.px
                padding = 5.px
                margin = Margin(0.px, 10.px)
              }
              ReactHTML.img {
                src = thumbnail.fileData
                css {
                  width = 100.pct
                  height = 170.px
                  objectFit = ObjectFit.contain
                  margin
                  objectPosition = ObjectPosition(50.pct, 50.pct)
                }
              }
              ReactHTML.h3 {
                +it.title
                css {
                  fontSize = FontSize(style.hFontSize4)
                  textOverflow = TextOverflow.ellipsis
                  margin = Margin(0.px, 5.px)
                  width = 100.pct
                }
              }
              ReactHTML.div {
                css {
                  display = Display.flex
                  justifyContent = JustifyContent.spaceBetween
                  margin = Margin(0.px, 5.px, 5.px, 5.px)
                }
                ReactHTML.p {
                  css {
                    textOverflow = TextOverflow.ellipsis
                    width = 100.pct
                  }
                  +"作者"
                }
                ReactHTML.img {
                  css {
                    width = 40.px
                    height = 40.px
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private suspend fun getPosts(): Result<List<GetPostsRes>> {
    return PostManager.getPosts(0, 16)
  }
}