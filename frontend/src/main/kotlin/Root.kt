import component.header.Header
import hook.theme.ThemeProvidor
import react.ChildrenBuilder
import react.Props
import react.dom.html.ReactHTML.div
import react.router.dom.BrowserRouter
import util.AbstractComponent

object Root : AbstractComponent<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    div {
      BrowserRouter {
        ThemeProvidor.fc {
          Header {}
        }
      }
    }
  }

}