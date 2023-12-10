package net.kigawa.fns.share.util.io

class CloseableHolderException(
  suppressed: List<Throwable>,
) : RuntimeException() {
  init {
    suppressed.forEach {
      addSuppressed(it)
    }
  }
}