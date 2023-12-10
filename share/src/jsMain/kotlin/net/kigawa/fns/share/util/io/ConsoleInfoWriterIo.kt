package net.kigawa.fns.share.util.io

class ConsoleInfoWriterIo : ConsoleWriterIo {
  override suspend fun writeLine(line: String) {
    console.info(line)
  }
}