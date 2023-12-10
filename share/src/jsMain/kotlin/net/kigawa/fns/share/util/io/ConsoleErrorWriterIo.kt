package net.kigawa.fns.share.util.io

class ConsoleErrorWriterIo : ConsoleWriterIo {
  override suspend fun writeLine(line: String) {
    console.error(line)
  }
}