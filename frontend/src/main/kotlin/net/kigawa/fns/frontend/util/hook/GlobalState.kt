package net.kigawa.fns.frontend.util.hook

import react.StateSetter
import react.useEffect
import react.useMemo
import react.useState

class GlobalState<T : Any?>(
  private var defaultValue: () -> T,
) {
  private val setters: MutableSet<StateSetter<T>> = mutableSetOf()

  constructor(defaultValue: T) : this({ defaultValue })

  fun use(): T {
    val (value, setter) = useState(defaultValue)

    console.info("use global state")
    val setterMemo = useMemo {
      setters.add(setter)

      setter
    }
    useEffect(setterMemo) {
      cleanup {
        setters.remove(setterMemo)
      }
    }

    return value
  }

  fun set(value: T) {
    this.defaultValue = { value }
    this.setters.forEach { it.invoke(value) }
  }

  override fun toString(): String {
    return "GlobalState(defaultValue=$defaultValue, setters=$setters)"
  }
}

