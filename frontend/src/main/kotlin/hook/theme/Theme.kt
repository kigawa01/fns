package hook.theme

import react.*

//val ThemeContext: Context<Theme> = React.createContext(initTheme())
private var setTheme: StateSetter<ThemeJson>? = null

external interface ThemeProps : Props {
}

class Theme : FC<ThemeProps> {
  override var displayName: String? = ""

}

val Root = FC<ThemeProps>("Welcome") { props ->
  val (theme, setTheme) = useState(ThemeJson())
  val (font, setFont) = useState("")
  hook.theme.setTheme = setTheme
  const nonNullTheme = theme == undefined ? initTheme () : theme

  useEffect(() => {
    highlight.initHighlighting()
  }, [])

  useEffect(() => {
    const promises = theme . fonts . map ((font) => {
    if (debug) console.debug(font)
    return new FontFace (font.name, "url("+font.url+")").load()
  })

    Promise.all(promises).then((fontFaces) => {
      fontFaces.forEach((font) => {
        (document.fonts as FontFaceSet).add(font)
      })
      let fontsStr = ""
      for (const font of theme.fonts) {
      fontsStr = fontsStr == "" ? fontsStr+font.name : fontsStr+","+font.name
    }
      setFont(fontsStr)
    }, (e: DOMException) => {
    console.error(e)
  })
  }, [theme])

  return < ThemeContext.Provider value ={ nonNullTheme } >
  <div style ={
  { color: nonNullTheme.textBase, backgroundColor: nonNullTheme.base,
    fontFamily: font
  }
} >
    { props.children }
  </div>
  </ThemeContext.Provider>
}

export function useTheme(): Theme {
  return useContext(ThemeContext)
}