package net.kigawa.fns.share.util.logger

import kotlin.reflect.KClass

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuLogger actual constructor(
  context: KClass<*>,
  handlers: List<LoggerHandler>,
  level: LogLevel?,
) {
  actual val level: LogLevel?
    get() = TODO("Not yet implemented")

  actual fun info(message: String) {
  }

  actual fun fine(message: String) {
  }

  actual fun warning(message: String) {
  }
}