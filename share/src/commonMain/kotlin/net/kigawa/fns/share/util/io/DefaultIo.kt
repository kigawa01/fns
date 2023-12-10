package net.kigawa.fns.share.util.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DefaultIo {
  val error: StringLineWriterIo
  val warn: StringLineWriterIo
  val info: StringLineWriterIo
}