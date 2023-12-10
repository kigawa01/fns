package net.kigawa.fns.share.util.io

@OptIn(ExperimentalStdlibApi::class)
interface KuCloseable : AutoCloseable {
  override fun close()
}
