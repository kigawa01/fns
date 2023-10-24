package net.kigawa.fns.backend.util

import io.ktor.server.application.*
import io.ktor.server.config.*
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.extention.UnitFinder
import net.kigawa.kutil.unitapi.options.FindOptions

class ConfigFinder(private val environment: ApplicationEnvironment) : UnitFinder {
  override fun <T : Any> findUnits(identify: UnitIdentify<T>, findOptions: FindOptions): List<T>? {
    if (!KutilReflect.instanceOf(identify.unitClass, ApplicationConfigValue::class.java)) return null

    @Suppress("UNCHECKED_CAST")
    return identify.name
      ?.let { environment.config.propertyOrNull(it) }
      ?.let { listOf(it as T) }
  }

}