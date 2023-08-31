package hook.theme

import emotion.react.css
import js.promise.Promise
import react.*
import react.dom.html.ReactHTML.div
import web.cssom.Color
import web.cssom.string
import web.fonts.FontFace

val ThemeContext: Context<ThemeJson> = createContext(ThemeJson())
private var setTheme: StateSetter<ThemeJson>? = null

external interface ThemeProviderProps : ProviderProps<ThemeJson> {
}

val ThemeProvider = FC<ThemeProviderProps>("ThemeProvider") { props ->
  val (theme, setTheme) = useState(ThemeJson())
  val (font, setFont) = useState("")
  hook.theme.setTheme = setTheme

  useEffect(theme) {
    val promises = theme.fonts.map { font ->
      return@map FontFace(font.name, "url(" + font.url + ")").load()
    }

    val fontsStr = theme.fonts.joinToString(separator = ",") { it.name }
    setFont(fontsStr)
    Promise.all(promises.toTypedArray()).then({}, { console.error(it) })
  }

  return@FC ThemeContext.Provider {
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

fun useTheme(): ThemeJson {
  return useContext(ThemeContext)
}