package net.kigawa.fns.share.util.logger

import java.util.logging.Logger
import kotlin.reflect.KClass

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuLogger actual constructor(
  context: KClass<*>,
  handlers: List<LoggerHandler>,
  actual val level: LogLevel?,
) : Logger(context.qualifiedName, null) {
  init {
    handlers.forEach {
      if (it !is ConsoleLoggerHandler) return@forEach
      addHandler(it.nativeHandler())
      it.setLevel(level ?: LogLevel.INFO)
    }
    setLevel(level?.javaLevel())
  }
}