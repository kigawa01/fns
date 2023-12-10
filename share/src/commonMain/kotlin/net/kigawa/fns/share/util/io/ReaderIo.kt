package net.kigawa.fns.share.util.io

import net.kigawa.fns.share.util.tryCatch

interface ReaderIo : InputIo {
  fun readLine(): String?
  suspend fun tryReadContinue(func: suspend (String) -> Unit) = tryCatch {
    readContinue(func)
  }


  suspend fun readContinue(func: suspend (String) -> Unit) {
    while (true) {
      val line: String = readLine() ?: return
      func(line)
    }
  }
}