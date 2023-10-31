package net.kigawa.fns.share.util

import kotlin.reflect.KClass
import kotlin.reflect.safeCast

object KutilResult {
  fun <T> tryResult(type: KClass<out Throwable>, func: () -> T): Result<T> {
    return try {
      Result.success(func())
    } catch (e: Throwable) {
      type.safeCast(e)
        ?.let { Result.failure(it) }
        ?: throw e
    }
  }
}