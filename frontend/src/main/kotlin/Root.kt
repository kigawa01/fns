import react.FC
import react.Props
import react.router.dom.BrowserRouter

external interface RootProps : Props {
}

val Root = FC<RootProps>("Welcome") { props ->
  BrowserRouter {
  }
}