package net.kigawa.fns.share.util.io

interface SuspendCloseable : KuCloseable {
  suspend fun suspendClose()
  override fun close()
}