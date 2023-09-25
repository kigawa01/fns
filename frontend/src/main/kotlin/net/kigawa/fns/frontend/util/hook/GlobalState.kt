package net.kigawa.fns.frontend.util.hook

import react.StateSetter
import react.router.dom.useBeforeUnload
import react.useEffect
import react.useState

class GlobalState<T : Any?>(
  private var defaultValue: () -> T
) {
  private val setters: MutableList<StateSetter<T>> = mutableListOf()

  constructor(defaultValue: T) : this({ defaultValue })

  fun use(): T {
    val (value, setter) = useState(defaultValue)

    useEffect {
      setters.add(setter)

      cleanup { setters.remove(setter) }
    }

    useBeforeUnload({ setters.remove(setter) })
    return value
  }

  fun set(value: T) {
    this.defaultValue = { value }
    this.setters.forEach { it.invoke(value) }
  }
}

