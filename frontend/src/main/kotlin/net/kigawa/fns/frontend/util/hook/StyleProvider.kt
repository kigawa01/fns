package net.kigawa.fns.frontend.util.hook

import emotion.react.Global
import emotion.react.css
import emotion.react.styles
import js.promise.Promise
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.share.json.StyleJson
import react.*
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import web.cssom.*
import web.cssom.Globals.Companion.unset
import web.fonts.FontFace

external interface ThemeProps : PropsWithChildren, PropsWithClassName
object StyleProvider : ComponentBase<ThemeProps>() {

  private val ThemeContext: Context<StyleJson> = createContext(StyleJson())
  private var themeSetter: StateSetter<StyleJson>? = null

  fun use(): StyleJson {
    return useContext(ThemeContext)
  }

  private fun loadFonts(theme: StyleJson, setFont: StateSetter<String>) {
    useEffect(theme) {
      val promises = theme.fonts.map { font ->
        return@map FontFace(font.name, "url(" + font.url + ")").load()
      }

      val fontsStr = theme.fonts.joinToString(separator = ",") { it.name }
      setFont(fontsStr)
      Promise.all(promises.toTypedArray()).then({}, { console.error(it) })
    }
  }

  private fun ChildrenBuilder.globalTheme(font: String) {
    Global {
      styles {
        ReactHTML.body {
          fontFamily = string(font)
        }
        (Selector("*")) {
          margin = 0.px
          padding = 0.px
          border = 0.px
        }
        ReactHTML.a {
          color = unset
          textDecoration = unset
        }
        ReactHTML.button {
          backgroundColor = unset
          fontFamily = string(font)
          cursor = Cursor.pointer
        }
        ReactHTML.h2 {
          fontFamily = string(font)
        }
        ReactHTML.li {
          listStyle = None.none
        }
        ReactHTML.strong {
          color = Color("red")
        }
        ReactHTML.img {
          display = Display.block
        }
      }
    }
  }

  override fun ChildrenBuilder.component(props: ThemeProps) {
    val (theme, setTheme) = useState(StyleJson())
    val (font, setFont) = useState("")
    themeSetter = setTheme

    loadFonts(theme, setFont)
    globalTheme(font)

    ThemeContext.Provider {
      value = theme
      div {
        className = props.className
        css {
          color = Color(theme.textBase)
          backgroundColor = Color(theme.base)
          fontFamily = string(font)
        }

        children = props.children
      }
    }
  }
}
