package net.kigawa.fns.share.util.io

import kotlinx.coroutines.channels.Channel
import net.kigawa.fns.share.util.concurrent.Coroutines

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DefaultIo {
  actual val info: StringLineWriterIo
  actual val error: StringLineWriterIo
  actual val warn: StringLineWriterIo

  init {
    val infoChannel = Channel<String>()
    val errorChannel = Channel<String>()
    val warnChannel = Channel<String>()

    info = ChannelWriterIo(infoChannel)
    warn = ChannelWriterIo(warnChannel)
    error = ChannelWriterIo(errorChannel)

    infoChannel.dispatchForEach(Coroutines.ioContext) {
      println(it)
    }
    warnChannel.dispatchForEach(Coroutines.ioContext) {
      System.err.println(it)
    }
    errorChannel.dispatchForEach(Coroutines.ioContext) {
      System.err.println(it)
    }
  }

}