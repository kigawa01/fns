package net.kigawa.fns.frontend.util.hook

import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.util.concurrent.Coroutines
import react.useEffectOnce
import react.useState

object ApiHook {
  inline fun <R> useApi(
    crossinline fetcher: suspend () -> Result<R>,
  ): ApiResult<R?> = useApi(null, fetcher)

  inline fun <R> useApi(
    defaultValue: R,
    crossinline fetcher: suspend () -> Result<R>,
  ): ApiResult<R> {
    val (err, setErr) = useState<String?>(null)
    val (result, setResult) = useState<R>(defaultValue)

    useEffectOnce {
      Coroutines.launchIo {
        val fetched = fetcher()
        fetched.getOrNull()?.let {
          setResult(it)
          return@launchIo
        }
        val e = fetched.exceptionOrNull() ?: throw RuntimeException()
        if (e is ErrIDException) {
          setErr(e.message)
          return@launchIo
        }
        setErr(e.message ?: e.toString())
      }
    }
    return ApiResult(err, result)
  }
}