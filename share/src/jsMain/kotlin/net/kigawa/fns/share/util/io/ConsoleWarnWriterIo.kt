package net.kigawa.fns.share.util.io

class ConsoleWarnWriterIo : ConsoleWriterIo {
  override suspend fun writeLine(line: String) {
    console.error(line)
  }
}