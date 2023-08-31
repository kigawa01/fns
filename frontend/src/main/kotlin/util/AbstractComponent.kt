package util

import react.ChildrenBuilder
import react.FC
import react.Props

abstract class AbstractComponent<T : Props>(displayName: String? = null) {
  val fc: FC<T>
  private val block: ChildrenBuilder.(props: T) -> Unit = { component(it) }

  init {
    fc = if (displayName == null) FC(block)
    else FC(displayName, block)
  }

  abstract fun ChildrenBuilder.component(props: T)
}