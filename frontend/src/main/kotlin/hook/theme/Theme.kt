package hook.theme

import emotion.react.css
import js.promise.Promise
import net.kigawa.fns.share.json.ThemeJson
import react.*
import react.dom.html.ReactHTML.div
import util.AbstractComponent
import web.cssom.Color
import web.cssom.string
import web.fonts.FontFace

object Theme : AbstractComponent<ProviderProps<ThemeJson>>() {

  private val ThemeContext: Context<ThemeJson> = createContext(ThemeJson())
  private var themeSetter: StateSetter<ThemeJson>? = null

  fun use(): ThemeJson {
    return useContext(ThemeContext)
  }

  override fun ChildrenBuilder.component(props: ProviderProps<ThemeJson>) {
    val (theme, setTheme) = useState(ThemeJson())
    val (font, setFont) = useState("")
    themeSetter = setTheme

    useEffect(theme) {
      val promises = theme.fonts.map { font ->
        return@map FontFace(font.name, "url(" + font.url + ")").load()
      }

      val fontsStr = theme.fonts.joinToString(separator = ",") { it.name }
      setFont(fontsStr)
      Promise.all(promises.toTypedArray()).then({}, { console.error(it) })
    }

    return ThemeContext.Provider {
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
