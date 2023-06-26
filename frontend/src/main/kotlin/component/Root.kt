package component

import react.FC
import react.Props
import react.dom.html.ReactHTML.h1

external interface RootProps: Props {
  var name: String?
}

val Root = FC<RootProps>("Welcome") {props->
  h1 {
    +"Hello, ${props.name}"
  }
}