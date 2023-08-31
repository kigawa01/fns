import component.header.Header
import hook.theme.Theme
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.dom.BrowserRouter

external interface RootProps : Props {
}

val Root = FC<RootProps>("Root") { props ->

  return@FC div {

    BrowserRouter {
      Theme.fc {
        Header {}
      }
    }
  }
}
