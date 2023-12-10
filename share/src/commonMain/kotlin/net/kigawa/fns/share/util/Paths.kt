package net.kigawa.fns.share.util

import net.kigawa.fns.share.util.io.KuPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect enum class Paths {
  JAVA
  ;

  val path: KuPath
}