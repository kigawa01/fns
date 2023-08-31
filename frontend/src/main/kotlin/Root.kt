import component.header.Header
import emotion.react.css
import hook.theme.ThemeProvider
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.style
import react.router.dom.BrowserRouter
import web.cssom.px

external interface RootProps : Props
{
}

val Root = FC<RootProps>("Root") { props ->

    return@FC div {

        BrowserRouter {
            ThemeProvider {
                Header {}
            }
        }
    }
}