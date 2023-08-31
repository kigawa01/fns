package component.header

import emotion.react.css
import hook.theme.useTheme
import react.FC
import react.Props
import react.dom.html.ReactHTML.header
import web.cssom.Color
import web.cssom.px

external interface HeaderProps : Props

val Header = FC<HeaderProps>("Header") { props ->
    val theme = useTheme()
    return@FC header {
        css {
            backgroundColor = Color(theme.base)
            height = 40.px
        }
    }
}