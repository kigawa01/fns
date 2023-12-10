package net.kigawa.fns.share.util.io

interface StringLineWriterIo {
  suspend fun writeLine(line: String)
}