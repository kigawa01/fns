package net.kigawa.fns.backend

import io.ktor.server.application.*
import io.ktor.server.netty.*
import net.kigawa.fns.backend.util.ConfigFinder
import net.kigawa.kutil.unitapi.component.UnitFinderComponent
import net.kigawa.kutil.unitapi.component.container.UnitContainer
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar

class FnsApplication {
  private val container = UnitContainer.create()
  fun loadModule(application: Application) {
    container.getUnit(InstanceRegistrar::class.java).apply {
      register(application)
      register(application.environment)
      register(application.log)
    }
    container.getUnit(UnitFinderComponent::class.java).apply {
      add(ConfigFinder::class.java)
    }
    container.getUnit(ResourceRegistrar::class.java).register(javaClass)
    container.getUnit(Modules::class.java).loadModule(application)
  }

  companion object {
    val app = FnsApplication()

    @JvmStatic
    fun main(args: Array<String>) {
      FnsApplication()
      EngineMain.main(args)
    }
  }
}
