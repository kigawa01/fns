package net.kigawa.fns.frontend.hook.theme

import emotion.react.Global
import emotion.react.css
import emotion.react.styles
import js.promise.Promise
import net.kigawa.fns.share.json.ThemeJson
import react.*
import react.dom.html.ReactHTML.div
import net.kigawa.fns.frontend.util.ComponentBase
import web.cssom.Color
import web.cssom.Globals.Companion.unset
import web.cssom.Selector
import web.cssom.px
import web.cssom.string
import web.fonts.FontFace

object ThemeProvidor : ComponentBase<ProviderProps<ThemeJson>>() {

  private val ThemeContext: Context<ThemeJson> = createContext(ThemeJson())
  private var themeSetter: StateSetter<ThemeJson>? = null

  fun use(): ThemeJson {
    return useContext(ThemeContext)
  }

  private fun loadFonts(theme: ThemeJson, setFont: StateSetter<String>) {
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
        (Selector("*")) {
          margin = 0.px
          padding = 0.px
          border = 0.px
        }

        (Selector("a")) {
          color = unset
          textDecoration = unset
        }

        (Selector("button")) {
          backgroundColor = unset
          fontFamily = string(font)
        }

        (Selector("p")) {
          fontFamily = string(font)
        }

        (Selector("h2")) {
          fontFamily = string(font)
        }
      }
    }
  }

  override fun ChildrenBuilder.component(props: ProviderProps<ThemeJson>) {
    val (theme, setTheme) = useState(ThemeJson())
    val (font, setFont) = useState("")
    themeSetter = setTheme

    loadFonts(theme, setFont)
    globalTheme(font)

    ThemeContext.Provider {
      value = theme
      div {
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
