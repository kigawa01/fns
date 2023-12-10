package net.kigawa.fns.share.util.io

import kotlinx.coroutines.withContext
import net.kigawa.fns.share.util.concurrent.Coroutines
import java.io.BufferedWriter

class BufferedWriterIo(
  private val bufferedWriter: BufferedWriter,
) : StringLineWriterIo {
  override suspend fun writeLine(line: String) {
    withContext(Coroutines.ioContext) {
      bufferedWriter.write(line)
      bufferedWriter.newLine()
      bufferedWriter.flush()
    }
  }
}