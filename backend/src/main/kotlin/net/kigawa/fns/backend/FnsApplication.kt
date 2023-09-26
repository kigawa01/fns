package net.kigawa.fns.backend

import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.kigawa.kutil.unitapi.component.container.UnitContainer
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar

class App {
  private val container = UnitContainer.create()
  fun registerModules(application: Application): Modules {
    container.getUnit(InstanceRegistrar::class.java).apply {
      register(application)
      register(application.environment)
    }
    container.getUnit(ResourceRegistrar::class.java).register(javaClass)
    return container.getUnit(Modules::class.java)
  }

  companion object {
    val app = App()

    @JvmStatic
    fun main(args: Array<String>) {
      App()
      EngineMain.main(args)
    }
  }
}
